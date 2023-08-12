package com.nctapplication.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.nctapplication.commons.ApiInterface
import com.nctapplication.repository.ClaimRepository
import com.nctapplication.repository.DashboardRepository
import com.nctapplication.repository.MemberRepository
import com.nctapplication.repository.ProductlistRepository
import com.nctapplication.response.ProductlistResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class ProductlistViewModel
@Inject
constructor(private val repository: ApiInterface): ViewModel() {
    var loginRepo: ProductlistRepository? = null
    private var mApiResponse: MediatorLiveData<ProductlistResponse>? = null

    init {
        loginRepo = ProductlistRepository(repository)
        mApiResponse = MediatorLiveData()
    }

    open fun product_category(): LiveData<ProductlistResponse?>? {
        //to detect coroutine error
        val exceptionHandler = CoroutineExceptionHandler { _, ex ->
            Log.e("CoroutineScope", "Caught ${Log.getStackTraceString(ex)}")
        }
        viewModelScope.launch(exceptionHandler) {
            loginRepo?.let {
                mApiResponse!!.addSource(
                    it.product_category(),
                    Observer<ProductlistResponse?> { apiResponse -> mApiResponse!!.setValue(apiResponse) })
            }
        }

        return mApiResponse
    }
}