package com.nctapplication.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.nctapplication.commons.ApiInterface
import com.nctapplication.repository.*
import com.nctapplication.response.AppDataResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class AppDataViewModel @Inject constructor(private val repository: ApiInterface): ViewModel() {
    var loginRepo: AppDataRepository? = null
    private var mApiResponse: MediatorLiveData<AppDataResponse>? = null

    init {
        loginRepo = AppDataRepository(repository)
        mApiResponse = MediatorLiveData()
    }

    open fun app_payout(requestBody: RequestBody): LiveData<AppDataResponse?>? {
        //to detect coroutine error
        val exceptionHandler = CoroutineExceptionHandler { _, ex ->
            Log.e("CoroutineScope", "Caught ${Log.getStackTraceString(ex)}")
        }
        viewModelScope.launch(exceptionHandler) {
            loginRepo?.let {
                mApiResponse!!.addSource(
                    it.app_payout(requestBody),
                    Observer<AppDataResponse?> { apiResponse -> mApiResponse!!.setValue(apiResponse) })
            }
        }

        return mApiResponse
    }
}