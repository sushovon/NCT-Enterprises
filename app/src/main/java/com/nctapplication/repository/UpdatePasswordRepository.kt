package com.nctapplication.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.example.dashboard.DashboardMember
import com.nctapplication.commons.ApiInterface
import com.nctapplication.model.claim.Claim
import com.nctapplication.model.memberaward.Member
import com.nctapplication.model.updatepassword.UpdatePassword
import com.nctapplication.response.UpdatePasswordResponse
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class UpdatePasswordRepository @Inject constructor(private val retroInstance: ApiInterface){
    val TAG = javaClass.simpleName

    fun updatepassword(requestBody: RequestBody): MutableLiveData<UpdatePasswordResponse> {
        var mutableList: MutableLiveData<UpdatePasswordResponse> = MutableLiveData()

        retroInstance.updatepassword(requestBody).enqueue(object: Callback<UpdatePassword> {

                override fun onResponse(call: Call<UpdatePassword>, response: Response<UpdatePassword>) {


                    if(response.isSuccessful && response.body()!=null){
                        mutableList.postValue(UpdatePasswordResponse(response.body()))
                    } else {
                        val error: Throwable? = null
                        mutableList.postValue(UpdatePasswordResponse(error))
                    }
                }

                override fun onFailure(call: Call<UpdatePassword>, t: Throwable) {
                    Log.e(TAG, "onFailure call="+call.toString()+"\n"+t.message )
                    mutableList.postValue(UpdatePasswordResponse(t))
                }

            })


        return mutableList
    }
}