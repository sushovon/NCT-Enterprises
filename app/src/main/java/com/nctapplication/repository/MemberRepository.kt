package com.nctapplication.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.example.dashboard.DashboardMember
import com.nctapplication.commons.ApiInterface
import com.nctapplication.model.memberaward.Member
import com.nctapplication.response.MemberResponse
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class MemberRepository @Inject constructor(private val retroInstance: ApiInterface){
    val TAG = javaClass.simpleName

    fun fetchmember(requestBody: RequestBody): MutableLiveData<MemberResponse> {
        var mutableList: MutableLiveData<MemberResponse> = MutableLiveData()

        retroInstance.member(requestBody).enqueue(object: Callback<Member> {

                override fun onResponse(call: Call<Member>, response: Response<Member>) {


                    if(response.isSuccessful && response.body()!=null){
                        mutableList.postValue(MemberResponse(response.body()))
                    } else {
                        val error: Throwable? = null
                        mutableList.postValue(MemberResponse(error))
                    }
                }

                override fun onFailure(call: Call<Member>, t: Throwable) {
                    Log.e(TAG, "onFailure call="+call.toString()+"\n"+t.message )
                    mutableList.postValue(MemberResponse(t))
                }

            })


        return mutableList
    }
}