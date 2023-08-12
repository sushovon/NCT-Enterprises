package com.nctapplication.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.nctapplication.commons.ApiInterface
import com.nctapplication.repository.ClaimRepository
import com.nctapplication.repository.DashboardRepository
import com.nctapplication.repository.MemberRepository
import com.nctapplication.repository.PriceRepository
import com.nctapplication.response.PriceResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class PriceViewModel @Inject constructor(private val repository: ApiInterface): ViewModel() {
    var loginRepo: PriceRepository? = null
    private var mApiResponse: MediatorLiveData<PriceResponse>? = null

    init {
        loginRepo = PriceRepository(repository)
        mApiResponse = MediatorLiveData()
    }

    open fun getprice(): LiveData<PriceResponse?>? {
        //to detect coroutine error
        val exceptionHandler = CoroutineExceptionHandler { _, ex ->
            Log.e("CoroutineScope", "Caught ${Log.getStackTraceString(ex)}")
        }
        viewModelScope.launch(exceptionHandler) {
            loginRepo?.let {
                mApiResponse!!.addSource(
                    it.price(),
                    Observer<PriceResponse?> { apiResponse -> mApiResponse!!.setValue(apiResponse) })
            }
        }

        return mApiResponse
    }
}