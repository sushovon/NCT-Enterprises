package com.nctapplication.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.nctapplication.commons.ApiInterface
import com.nctapplication.repository.*
import com.nctapplication.response.TransactionDetailsResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class TransactionDetailsViewModel @Inject constructor(private val repository: ApiInterface): ViewModel() {
    var loginRepo: TransactionDetailsRepository? = null
    private var mApiResponse: MediatorLiveData<TransactionDetailsResponse>? = null

    init {
        loginRepo = TransactionDetailsRepository(repository)
        mApiResponse = MediatorLiveData()
    }

    open fun insert_transactiondtls(requestBody: RequestBody): LiveData<TransactionDetailsResponse?>? {
        //to detect coroutine error
        val exceptionHandler = CoroutineExceptionHandler { _, ex ->
            Log.e("CoroutineScope", "Caught ${Log.getStackTraceString(ex)}")
        }
        viewModelScope.launch(exceptionHandler) {
            loginRepo?.let {
                mApiResponse!!.addSource(
                    it.insert_transactiondtls(requestBody),
                    Observer<TransactionDetailsResponse?> { apiResponse -> mApiResponse!!.setValue(apiResponse) })
            }
        }

        return mApiResponse
    }
}