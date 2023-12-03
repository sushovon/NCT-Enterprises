package com.nctapplication.viewmodel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nctapplication.commons.Commonfun
import com.nctapplication.commons.Resource
import com.nctapplication.model.Resend
import com.nctapplication.repository.ResendRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class ResendViewModel @Inject constructor(private val repository: ResendRepository) : ViewModel() {

    val resenddata: LiveData<Resource<Resend>>
        get() = repository.reportData

    fun onLoginClicked(view : View){
            viewModelScope.launch(Dispatchers.IO) {
                val requestBody: RequestBody = MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("member_phoneno", Commonfun.mobile)
                    .build()
                repository.report(requestBody)
        }

    }
}