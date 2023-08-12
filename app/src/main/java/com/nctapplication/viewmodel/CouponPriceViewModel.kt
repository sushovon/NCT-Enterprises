package com.nctapplication.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.nctapplication.commons.ApiInterface
import com.nctapplication.repository.CouponPriceRepository
import com.nctapplication.response.CouponPriceResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import okhttp3.RequestBody
import javax.inject.Inject
@HiltViewModel
class CouponPriceViewModel@Inject constructor(private val repository: ApiInterface): ViewModel() {
    var loginRepo: CouponPriceRepository? = null
    private var mApiResponse: MediatorLiveData<CouponPriceResponse>? = null

    init {
        loginRepo = CouponPriceRepository(repository)
        mApiResponse = MediatorLiveData()
    }

    open fun coupon_price(requestBody: RequestBody): LiveData<CouponPriceResponse?>? {
        //to detect coroutine error
        val exceptionHandler = CoroutineExceptionHandler { _, ex ->
            Log.e("CoroutineScope", "Caught ${Log.getStackTraceString(ex)}")
        }
        viewModelScope.launch(exceptionHandler) {
            loginRepo?.let {
                mApiResponse!!.addSource(
                    it.coupon_price(requestBody),
                    Observer<CouponPriceResponse?> { apiResponse -> mApiResponse!!.setValue(apiResponse) })
            }
        }

        return mApiResponse
    }
}