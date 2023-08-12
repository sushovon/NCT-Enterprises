package com.nctapplication.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.example.dashboard.DashboardMember
import com.nctapplication.commons.ApiInterface
import com.nctapplication.model.claim.Claim
import com.nctapplication.model.memberaward.Member
import com.nctapplication.response.ClaimApiResponse
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class ClaimRepository @Inject constructor(private val retroInstance: ApiInterface){
    val TAG = javaClass.simpleName

    fun fetchclaim(requestBody: RequestBody): MutableLiveData<ClaimApiResponse> {
        var mutableList: MutableLiveData<ClaimApiResponse> = MutableLiveData()

        retroInstance.claim(requestBody).enqueue(object: Callback<Claim> {

                override fun onResponse(call: Call<Claim>, response: Response<Claim>) {


                    if(response.isSuccessful && response.body()!=null){
                        mutableList.postValue(ClaimApiResponse(response.body()))
                    } else {
                        val error: Throwable? = null
                        mutableList.postValue(ClaimApiResponse(error))
                    }
                }

                override fun onFailure(call: Call<Claim>, t: Throwable) {
                    Log.e(TAG, "onFailure call="+call.toString()+"\n"+t.message )
                    mutableList.postValue(ClaimApiResponse(t))
                }

            })


        return mutableList
    }
}