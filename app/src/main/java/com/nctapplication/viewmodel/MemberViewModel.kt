package com.nctapplication.viewmodel

import androidx.lifecycle.*
import com.nctapplication.commons.ApiInterface
import com.nctapplication.repository.DashboardRepository
import com.nctapplication.repository.MemberRepository
import com.nctapplication.response.MemberResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class MemberViewModel @Inject constructor(private val repository: ApiInterface): ViewModel() {
    var loginRepo: MemberRepository? = null
    private var mApiResponse: MediatorLiveData<MemberResponse>? = null

    init {
        loginRepo = MemberRepository(repository)
        mApiResponse = MediatorLiveData()
    }

    open fun getMember(requestBody: RequestBody): LiveData<MemberResponse?>? {
        viewModelScope.launch {
            loginRepo?.let {
                mApiResponse!!.addSource(
                    it.fetchmember(requestBody),
                    Observer<MemberResponse?> { apiResponse -> mApiResponse!!.setValue(apiResponse) })
            }
        }

        return mApiResponse
    }
}