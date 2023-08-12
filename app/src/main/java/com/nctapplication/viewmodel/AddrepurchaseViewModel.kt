package com.nctapplication.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.nctapplication.commons.ApiInterface
import com.nctapplication.repository.*
import com.nctapplication.response.AddrepurchaseResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class AddrepurchaseViewModel @Inject constructor(private val repository: ApiInterface): ViewModel() {
    var loginRepo: AddrepurchaseRepository? = null
    private var mApiResponse: MediatorLiveData<AddrepurchaseResponse>? = null

    init {
        loginRepo = AddrepurchaseRepository(repository)
        mApiResponse = MediatorLiveData()
    }

    open fun add_repurchase(requestBody: RequestBody): LiveData<AddrepurchaseResponse?>? {
        //to detect coroutine error
        val exceptionHandler = CoroutineExceptionHandler { _, ex ->
            Log.e("CoroutineScope", "Caught ${Log.getStackTraceString(ex)}")
        }
        viewModelScope.launch(exceptionHandler) {
            loginRepo?.let {
                mApiResponse!!.addSource(
                    it.add_repurchase(requestBody),
                    Observer<AddrepurchaseResponse?> { apiResponse -> mApiResponse!!.setValue(apiResponse) })
            }
        }

        return mApiResponse
    }
}