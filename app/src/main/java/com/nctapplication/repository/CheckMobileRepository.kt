package com.nctapplication.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.example.dashboard.DashboardMember
import com.nctapplication.commons.ApiInterface
import com.nctapplication.model.checkmobile.CheckMobile
import com.nctapplication.model.claim.Claim
import com.nctapplication.model.memberaward.Member
import com.nctapplication.response.CheckMobileResponse
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class CheckMobileRepository @Inject constructor(private val retroInstance: ApiInterface){
    val TAG = javaClass.simpleName

    fun checkmobile(requestBody: RequestBody): MutableLiveData<CheckMobileResponse> {
        var mutableList: MutableLiveData<CheckMobileResponse> = MutableLiveData()

        retroInstance.checkmobile(requestBody).enqueue(object: Callback<CheckMobile> {

                override fun onResponse(call: Call<CheckMobile>, response: Response<CheckMobile>) {


                    if(response.isSuccessful && response.body()!=null){
                        mutableList.postValue(CheckMobileResponse(response.body()))
                    } else {
                        val error: Throwable? = null
                        mutableList.postValue(CheckMobileResponse(error))
                    }
                }

                override fun onFailure(call: Call<CheckMobile>, t: Throwable) {
                    Log.e(TAG, "onFailure call="+call.toString()+"\n"+t.message )
                    mutableList.postValue(CheckMobileResponse(t))
                }

            })


        return mutableList
    }
}