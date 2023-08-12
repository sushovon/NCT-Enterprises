package com.nctapplication.viewmodel

import androidx.lifecycle.*
import com.nctapplication.model.Login
import com.nctapplication.repository.LoginRepository
import com.nctapplication.response.ApiResponse
import okhttp3.RequestBody

class LoginViewModel():ViewModel() {
    var loginRepo: LoginRepository? = null
    private var mApiResponse: MediatorLiveData<ApiResponse>? = null

    init {
        loginRepo = LoginRepository()
        mApiResponse = MediatorLiveData()
    }
    open fun getLogin(requestBody: RequestBody): LiveData<ApiResponse?>? {
        loginRepo?.let {
            mApiResponse!!.addSource(
                it.fetchLogin(requestBody),
                Observer<ApiResponse?> { apiResponse -> mApiResponse!!.setValue(apiResponse) })
        }
        return mApiResponse
    }
}