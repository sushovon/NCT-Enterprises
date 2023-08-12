package com.nctapplication.viewmodel

import androidx.lifecycle.*
import com.nctapplication.commons.ApiInterface
import com.nctapplication.repository.DashboardRepository
import com.nctapplication.repository.ProfileRepository
import com.nctapplication.repository.WalletRepository
import com.nctapplication.response.WalletApiResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class WalletViewModel
@Inject
constructor(private val repository: ApiInterface): ViewModel() {
    var Repo: WalletRepository? = null
    private var mApiResponse: MediatorLiveData<WalletApiResponse>? = null

    init {
        Repo = WalletRepository(repository)
        mApiResponse = MediatorLiveData()
    }

    open fun getWallet(requestBody: RequestBody): LiveData<WalletApiResponse?>? {
        Repo?.let {
            mApiResponse!!.addSource(
                it.fetchWallet(requestBody),
                Observer<WalletApiResponse?> { apiResponse -> mApiResponse!!.setValue(apiResponse) })
        }
        return mApiResponse
    }
}