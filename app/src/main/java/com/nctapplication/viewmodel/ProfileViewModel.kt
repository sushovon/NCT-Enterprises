package com.nctapplication.viewmodel

import androidx.lifecycle.*
import com.nctapplication.commons.ApiInterface
import com.nctapplication.repository.DashboardRepository
import com.nctapplication.repository.ProfileRepository
import com.nctapplication.response.ProfileApiResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val repository: ApiInterface): ViewModel() {
    var loginRepo: ProfileRepository? = null
    private var mApiResponse: MediatorLiveData<ProfileApiResponse>? = null

    init {
        loginRepo = ProfileRepository(repository)
        mApiResponse = MediatorLiveData()
    }

    open fun getProfile(requestBody: RequestBody): LiveData<ProfileApiResponse?>? {
        loginRepo?.let {
            mApiResponse!!.addSource(
                it.fetchProfile(requestBody),
                Observer<ProfileApiResponse?> { apiResponse -> mApiResponse!!.setValue(apiResponse) })
        }
        return mApiResponse
    }
}