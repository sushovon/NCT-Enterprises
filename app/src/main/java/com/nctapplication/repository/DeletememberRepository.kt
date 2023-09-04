package com.nctapplication.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nctapplication.commons.ApiInterface
import com.nctapplication.model.deletemember.Memberdelete
import okhttp3.RequestBody
import javax.inject.Inject

class DeletememberRepository @Inject constructor(private val retroInstance: ApiInterface) {
private val deletememberLiveData= MutableLiveData<Memberdelete>()

    val deletemember: LiveData<Memberdelete>
        get() = deletememberLiveData

    suspend fun deletemember(body: RequestBody){
        val result=retroInstance.delete_member(body)
        if(result?.body()!=null){
            deletememberLiveData.postValue(result.body())
        }
    }

}