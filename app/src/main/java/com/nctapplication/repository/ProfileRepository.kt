package com.nctapplication.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.example.dashboard.DashboardMember
import com.nctapplication.commons.ApiInterface
import com.nctapplication.model.profile.ProfileData
import com.nctapplication.response.ProfileApiResponse
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class ProfileRepository @Inject constructor(private val retroInstance: ApiInterface){
    val TAG = javaClass.simpleName

    fun fetchProfile(requestBody: RequestBody): MutableLiveData<ProfileApiResponse> {
        var mutableList: MutableLiveData<ProfileApiResponse> = MutableLiveData()

        retroInstance.profile(requestBody).enqueue(object: Callback<ProfileData> {

                override fun onResponse(call: Call<ProfileData>, response: Response<ProfileData>) {


                    if(response.isSuccessful && response.body()!=null){
                        mutableList.postValue(ProfileApiResponse(response.body()))
                    } else {
                        val error: Throwable? = null
                        mutableList.postValue(ProfileApiResponse(error))
                    }
                }

                override fun onFailure(call: Call<ProfileData>, t: Throwable) {
                    Log.e(TAG, "onFailure call="+call.toString()+"\n"+t.message )
                    mutableList.postValue(ProfileApiResponse(t))
                }

            })


        return mutableList
    }
}