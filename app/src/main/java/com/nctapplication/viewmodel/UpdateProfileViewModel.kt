package com.nctapplication.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.nctapplication.commons.ApiInterface
import com.nctapplication.repository.ClaimRepository
import com.nctapplication.repository.DashboardRepository
import com.nctapplication.repository.MemberRepository
import com.nctapplication.repository.ProfileUpdateRepository
import com.nctapplication.response.ProfileUpdateResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class UpdateProfileViewModel @Inject constructor(private val repository: ApiInterface): ViewModel() {
    var loginRepo: ProfileUpdateRepository? = null
    private var mApiResponse: MediatorLiveData<ProfileUpdateResponse>? = null

    init {
        loginRepo = ProfileUpdateRepository(repository)
        mApiResponse = MediatorLiveData()
    }

    open fun updateprofile(id: RequestBody,email: RequestBody, image: RequestBody): LiveData<ProfileUpdateResponse?>? {
        //to detect coroutine error
        val exceptionHandler = CoroutineExceptionHandler { _, ex ->
            Log.e("CoroutineScope", "Caught ${Log.getStackTraceString(ex)}")
        }
        viewModelScope.launch(exceptionHandler) {
            loginRepo?.let {
                mApiResponse!!.addSource(
                    it.updateprofile(id, email, image),
                    Observer<ProfileUpdateResponse?> { apiResponse -> mApiResponse!!.setValue(apiResponse) })
            }
        }

        return mApiResponse
    }
}