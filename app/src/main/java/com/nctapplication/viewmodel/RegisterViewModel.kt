package com.nctapplication.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.nctapplication.commons.ApiInterface
import com.nctapplication.repository.ClaimRepository
import com.nctapplication.repository.DashboardRepository
import com.nctapplication.repository.MemberRepository
import com.nctapplication.repository.RegisterRepository
import com.nctapplication.response.RegisterResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val repository: ApiInterface): ViewModel() {
    var loginRepo: RegisterRepository? = null
    private var mApiResponse: MediatorLiveData<RegisterResponse>? = null

    init {
        loginRepo = RegisterRepository(repository)
        mApiResponse = MediatorLiveData()
    }

    open fun register(first_name: RequestBody,last_name: RequestBody,phoneno: RequestBody ,email:RequestBody,password: RequestBody,
                      referral_code: RequestBody,aadharcard: RequestBody,pancard: RequestBody,file: RequestBody,adharfile: RequestBody): LiveData<RegisterResponse?>? {
        //to detect coroutine error
        val exceptionHandler = CoroutineExceptionHandler { _, ex ->
            Log.e("CoroutineScope", "Caught ${Log.getStackTraceString(ex)}")
        }
        viewModelScope.launch(exceptionHandler) {
            loginRepo?.let {
                mApiResponse!!.addSource(
                    it.register(first_name,last_name,phoneno,email,password,referral_code,aadharcard,pancard,file,adharfile),
                    Observer<RegisterResponse?> { apiResponse -> mApiResponse!!.setValue(apiResponse) })
            }
        }

        return mApiResponse
    }
}