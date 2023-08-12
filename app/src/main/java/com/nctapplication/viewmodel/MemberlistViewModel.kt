package com.nctapplication.viewmodel

import androidx.lifecycle.*
import com.nctapplication.commons.ApiInterface
import com.nctapplication.repository.ClaimRepository
import com.nctapplication.repository.DashboardRepository
import com.nctapplication.repository.MemberRepository
import com.nctapplication.repository.MemberlistRepository
import com.nctapplication.response.MemberListResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class MemberlistViewModel @Inject constructor(private val repository: ApiInterface): ViewModel() {
    var loginRepo: MemberlistRepository? = null
    private var mApiResponse: MediatorLiveData<MemberListResponse>? = null

    init {
        loginRepo = MemberlistRepository(repository)
        mApiResponse = MediatorLiveData()
    }

    open fun getmember(): LiveData<MemberListResponse?>? {
        viewModelScope.launch {
            loginRepo?.let {
                mApiResponse!!.addSource(
                    it.fetchmember(),
                    Observer<MemberListResponse?> { apiResponse -> mApiResponse!!.setValue(apiResponse) })
            }
        }

        return mApiResponse
    }
}