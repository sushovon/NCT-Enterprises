package com.nctapplication.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.nctapplication.commons.ApiInterface
import com.nctapplication.repository.ClaimRepository
import com.nctapplication.repository.DashboardRepository
import com.nctapplication.repository.MemberRepository
import com.nctapplication.response.ClaimApiResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class ClaimViewModel @Inject constructor(private val repository: ApiInterface): ViewModel() {
    var loginRepo: ClaimRepository? = null
    private var mApiResponse: MediatorLiveData<ClaimApiResponse>? = null

    init {
        loginRepo = ClaimRepository(repository)
        mApiResponse = MediatorLiveData()
    }

    open fun getclaim(requestBody: RequestBody): LiveData<ClaimApiResponse?>? {
        //to detect coroutine error
        val exceptionHandler = CoroutineExceptionHandler { _, ex ->
            Log.e("CoroutineScope", "Caught ${Log.getStackTraceString(ex)}")
        }
        viewModelScope.launch(exceptionHandler) {
            loginRepo?.let {
                mApiResponse!!.addSource(
                    it.fetchclaim(requestBody),
                    Observer<ClaimApiResponse?> { apiResponse -> mApiResponse!!.setValue(apiResponse) })
            }
        }

        return mApiResponse
    }
}