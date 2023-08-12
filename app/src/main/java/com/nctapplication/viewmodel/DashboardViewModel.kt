package com.nctapplication.viewmodel

import androidx.lifecycle.*
import com.nctapplication.commons.ApiInterface
import com.nctapplication.repository.DashboardRepository
import com.nctapplication.response.DashboardApiResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(private val repository: ApiInterface): ViewModel() {
    var loginRepo: DashboardRepository? = null
    private var mApiResponse: MediatorLiveData<DashboardApiResponse>? = null

    init {
        loginRepo = DashboardRepository(repository)
        mApiResponse = MediatorLiveData()
    }

    open fun getDashboard(requestBody: RequestBody): LiveData<DashboardApiResponse?>? {
        loginRepo?.let {
            mApiResponse!!.addSource(
                it.fetchDashboard(requestBody),
                Observer<DashboardApiResponse?> { apiResponse -> mApiResponse!!.setValue(apiResponse) })
        }
        return mApiResponse
    }
}