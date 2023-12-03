package com.nctapplication.viewmodel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nctapplication.commons.Commonfun
import com.nctapplication.commons.Resource
import com.nctapplication.model.Resend
import com.nctapplication.repository.ResendRepository
import com.nctapplication.repository.ValidateOTPRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class ValidateOTPViewModel @Inject constructor(private val repository: ValidateOTPRepository) : ViewModel() {

    val validatedata: LiveData<Resource<Resend>>
        get() = repository.validate_otpData

    fun validate(otp : String){
            viewModelScope.launch(Dispatchers.IO) {
                val requestBody: RequestBody = MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("member_phoneno", Commonfun.mobile)
                    .addFormDataPart("otp", otp)
                    .build()
                repository.validate_otp(requestBody)
        }

    }
}