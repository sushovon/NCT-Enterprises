package com.nctapplication.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nctapplication.commons.ApiInterface
import com.nctapplication.commons.Resource
import com.nctapplication.model.Resend
import okhttp3.RequestBody
import javax.inject.Inject

class ValidateOTPRepository @Inject constructor(private val retroInstance: ApiInterface) {
private val reportLiveData= MutableLiveData<Resource<Resend>>()

    val validate_otpData: LiveData<Resource<Resend>>
        get() = reportLiveData

    suspend fun validate_otp(body: RequestBody) {
        reportLiveData.postValue(Resource.Loading())
        try {
            val result = retroInstance.validate_otp(body)
            reportLiveData.postValue(Resource.Success(data = result))
        } catch (exception: Exception) {
            reportLiveData.postValue(Resource.Error(message = "An error occurred ${exception.message.toString()}"))

        }
    }

}