package com.nctapplication.viewmodel

import androidx.lifecycle.*
import com.nctapplication.commons.ApiInterface
import com.nctapplication.repository.*
import com.nctapplication.response.DirectsaleResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class DirectsaleViewModel
@Inject
constructor(private val repository: ApiInterface): ViewModel() {
    var Repo: DirectsaleRepository? = null
    private var mApiResponse: MediatorLiveData<DirectsaleResponse>? = null

    init {
        Repo = DirectsaleRepository(repository)
        mApiResponse = MediatorLiveData()
    }

    open fun getDirectsale(requestBody: RequestBody): LiveData<DirectsaleResponse?>? {
        Repo?.let {
            mApiResponse!!.addSource(
                it.fetchDirectsale(requestBody),
                Observer<DirectsaleResponse?> { apiResponse -> mApiResponse!!.setValue(apiResponse) })
        }
        return mApiResponse
    }
}