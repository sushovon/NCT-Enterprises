package com.nctapplication.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.nctapplication.commons.ApiInterface
import com.nctapplication.repository.CheckMobileRepository
import com.nctapplication.repository.ClaimRepository
import com.nctapplication.repository.DashboardRepository
import com.nctapplication.repository.MemberRepository
import com.nctapplication.response.CheckMobileResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class CheckMobileViewModel @Inject constructor(private val repository: ApiInterface): ViewModel() {
    var loginRepo: CheckMobileRepository? = null
    private var mApiResponse: MediatorLiveData<CheckMobileResponse>? = null

    init {
        loginRepo = CheckMobileRepository(repository)
        mApiResponse = MediatorLiveData()
    }

    open fun checkmobile(requestBody: RequestBody): LiveData<CheckMobileResponse?>? {
        //to detect coroutine error
        val exceptionHandler = CoroutineExceptionHandler { _, ex ->
            Log.e("CoroutineScope", "Caught ${Log.getStackTraceString(ex)}")
        }
        viewModelScope.launch(exceptionHandler) {
            loginRepo?.let {
                mApiResponse!!.addSource(
                    it.checkmobile(requestBody),
                    Observer<CheckMobileResponse?> { apiResponse -> mApiResponse!!.setValue(apiResponse) })
            }
        }

        return mApiResponse
    }
}