package com.nctapplication.viewmodel

import androidx.lifecycle.*
import com.nctapplication.commons.ApiInterface
import com.nctapplication.repository.ClaimRepository
import com.nctapplication.repository.DashboardRepository
import com.nctapplication.repository.MemberRepository
import com.nctapplication.repository.PayoutRepository
import com.nctapplication.response.PayoutResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class PayoutViewModel @Inject constructor(private val repository: ApiInterface): ViewModel() {
    var loginRepo: PayoutRepository? = null
    private var mApiResponse: MediatorLiveData<PayoutResponse>? = null

    init {
        loginRepo = PayoutRepository(repository)
        mApiResponse = MediatorLiveData()
    }

    open fun getpayout(requestBody: RequestBody): LiveData<PayoutResponse?>? {
        viewModelScope.launch {
            loginRepo?.let {
                mApiResponse!!.addSource(
                    it.fetchPayout(requestBody),
                    Observer<PayoutResponse?> { apiResponse -> mApiResponse!!.setValue(apiResponse) })
            }
        }

        return mApiResponse
    }
}