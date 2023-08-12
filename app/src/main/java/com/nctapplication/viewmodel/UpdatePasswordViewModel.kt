package com.nctapplication.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.nctapplication.commons.ApiInterface
import com.nctapplication.repository.ClaimRepository
import com.nctapplication.repository.DashboardRepository
import com.nctapplication.repository.MemberRepository
import com.nctapplication.repository.UpdatePasswordRepository
import com.nctapplication.response.UpdatePasswordResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class UpdatePasswordViewModel @Inject constructor(private val repository: ApiInterface): ViewModel() {
    var loginRepo: UpdatePasswordRepository? = null
    private var mApiResponse: MediatorLiveData<UpdatePasswordResponse>? = null

    init {
        loginRepo = UpdatePasswordRepository(repository)
        mApiResponse = MediatorLiveData()
    }

    open fun updatepassword(requestBody: RequestBody): LiveData<UpdatePasswordResponse?>? {
        //to detect coroutine error
        val exceptionHandler = CoroutineExceptionHandler { _, ex ->
            Log.e("CoroutineScope", "Caught ${Log.getStackTraceString(ex)}")
        }
        viewModelScope.launch(exceptionHandler) {
            loginRepo?.let {
                mApiResponse!!.addSource(
                    it.updatepassword(requestBody),
                    Observer<UpdatePasswordResponse?> { apiResponse -> mApiResponse!!.setValue(apiResponse) })
            }
        }

        return mApiResponse
    }
}