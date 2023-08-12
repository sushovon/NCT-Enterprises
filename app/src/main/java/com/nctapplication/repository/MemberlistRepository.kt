package com.nctapplication.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.nctapplication.commons.ApiInterface
import com.nctapplication.model.member.MemberList
import com.nctapplication.response.MemberListResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class MemberlistRepository @Inject constructor(private val retroInstance: ApiInterface){
    val TAG = javaClass.simpleName

    fun fetchmember(): MutableLiveData<MemberListResponse> {
        var mutableList: MutableLiveData<MemberListResponse> = MutableLiveData()

        retroInstance.member().enqueue(object: Callback<MemberList> {

                override fun onResponse(call: Call<MemberList>, response: Response<MemberList>) {


                    if(response.isSuccessful && response.body()!=null){
                        mutableList.postValue(MemberListResponse(response.body()))
                    } else {
                        val error: Throwable? = null
                        mutableList.postValue(MemberListResponse(error))
                    }
                }

                override fun onFailure(call: Call<MemberList>, t: Throwable) {
                    Log.e(TAG, "onFailure call="+call.toString()+"\n"+t.message )
                    mutableList.postValue(MemberListResponse(t))
                }

            })


        return mutableList
    }
}