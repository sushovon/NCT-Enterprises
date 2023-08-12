package com.nctapplication.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.example.dashboard.DashboardMember
import com.nctapplication.commons.ApiInterface
import com.nctapplication.model.claim.Claim
import com.nctapplication.model.memberaward.Member
import com.nctapplication.model.profileupdate.Profileupdate
import com.nctapplication.response.ProfileUpdateResponse
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class ProfileUpdateRepository @Inject constructor(private val retroInstance: ApiInterface){
    val TAG = javaClass.simpleName

    fun updateprofile( id: RequestBody,email: RequestBody, image: RequestBody): MutableLiveData<ProfileUpdateResponse> {
        var mutableList: MutableLiveData<ProfileUpdateResponse> = MutableLiveData()

        retroInstance.updateprofile(id, email, image).enqueue(object: Callback<Profileupdate> {

                override fun onResponse(call: Call<Profileupdate>, response: Response<Profileupdate>) {


                    if(response.isSuccessful && response.body()!=null){
                        mutableList.postValue(ProfileUpdateResponse(response.body()))
                    } else {
                        val error: Throwable? = null
                        mutableList.postValue(ProfileUpdateResponse(error))
                    }
                }

                override fun onFailure(call: Call<Profileupdate>, t: Throwable) {
                    Log.e(TAG, "onFailure call="+call.toString()+"\n"+t.message )
                    mutableList.postValue(ProfileUpdateResponse(t))
                }

            })


        return mutableList
    }
}