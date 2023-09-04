package com.nctapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nctapplication.commons.ApiInterface
import com.nctapplication.model.deletemember.Memberdelete
import com.nctapplication.repository.DeletememberRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.paperdb.Paper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject
@HiltViewModel
class DeletememberViewmodel @Inject constructor(private val repository: DeletememberRepository):ViewModel() {
    fun deletemember(){
        viewModelScope.launch(Dispatchers.IO) {
            val requestBody: RequestBody = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("member_id", Paper.book().read<Int>("memberid", 0).toString())
                .build()
            repository.deletemember(requestBody)
        }
    }
    val deletedata: LiveData<Memberdelete>
        get() = repository.deletemember
}