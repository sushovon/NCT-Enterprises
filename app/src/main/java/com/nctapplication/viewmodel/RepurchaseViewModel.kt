package com.nctapplication.viewmodel

import androidx.lifecycle.*
import com.nctapplication.commons.ApiInterface
import com.nctapplication.repository.DashboardRepository
import com.nctapplication.repository.ProfileRepository
import com.nctapplication.repository.RepurchaseRepository
import com.nctapplication.repository.WalletRepository
import com.nctapplication.response.RepurchaseApiResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class RepurchaseViewModel
@Inject
constructor(private val repository: ApiInterface): ViewModel() {
    var Repo: RepurchaseRepository? = null
    private var mApiResponse: MediatorLiveData<RepurchaseApiResponse>? = null

    init {
        Repo = RepurchaseRepository(repository)
        mApiResponse = MediatorLiveData()
    }

    open fun getRepurchase(requestBody: RequestBody): LiveData<RepurchaseApiResponse?>? {
        Repo?.let {
            mApiResponse!!.addSource(
                it.fetchRepurchase(requestBody),
                Observer<RepurchaseApiResponse?> { apiResponse -> mApiResponse!!.setValue(apiResponse) })
        }
        return mApiResponse
    }
}