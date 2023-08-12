package com.nctapplication.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.example.dashboard.DashboardMember
import com.nctapplication.commons.ApiInterface
import com.nctapplication.model.claim.Claim
import com.nctapplication.model.memberaward.Member
import com.nctapplication.model.register.Register
import com.nctapplication.response.RegisterResponse
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class RegisterRepository @Inject constructor(private val retroInstance: ApiInterface){
    val TAG = javaClass.simpleName

    fun register(first_name: RequestBody,last_name: RequestBody,phoneno: RequestBody ,email:RequestBody,password: RequestBody,
                   referral_code: RequestBody,aadharcard: RequestBody,pancard: RequestBody,file: RequestBody,adharfile: RequestBody
    ): MutableLiveData<RegisterResponse> {
        var mutableList: MutableLiveData<RegisterResponse> = MutableLiveData()

        retroInstance.register(first_name,last_name,phoneno,email,password,referral_code,aadharcard,pancard,file,adharfile).enqueue(object: Callback<Register> {

                override fun onResponse(call: Call<Register>, response: Response<Register>) {


                    if(response.isSuccessful && response.body()!=null){
                        mutableList.postValue(RegisterResponse(response.body()))
                    } else {
                        val error: Throwable? = null
                        mutableList.postValue(RegisterResponse(error))
                    }
                }

                override fun onFailure(call: Call<Register>, t: Throwable) {
                    Log.e(TAG, "onFailure call="+call.toString()+"\n"+t.message )
                    mutableList.postValue(RegisterResponse(t))
                }

            })


        return mutableList
    }
}