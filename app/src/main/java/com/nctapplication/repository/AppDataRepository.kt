package com.nctapplication.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.example.dashboard.DashboardMember
import com.nctapplication.commons.ApiInterface
import com.nctapplication.model.addrepurchase.Addrepurchase
import com.nctapplication.model.appdata.AppData
import com.nctapplication.model.checkmobile.CheckMobile
import com.nctapplication.model.claim.Claim
import com.nctapplication.model.memberaward.Member
import com.nctapplication.response.AppDataResponse
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class AppDataRepository @Inject constructor(private val retroInstance: ApiInterface){
    val TAG = javaClass.simpleName

    fun app_payout(requestBody: RequestBody): MutableLiveData<AppDataResponse> {
        var mutableList: MutableLiveData<AppDataResponse> = MutableLiveData()

        retroInstance.app_payout(requestBody).enqueue(object: Callback<AppData> {

                override fun onResponse(call: Call<AppData>, response: Response<AppData>) {


                    if(response.isSuccessful && response.body()!=null){
                        mutableList.postValue(AppDataResponse(response.body()))
                    } else {
                        val error: Throwable? = null
                        mutableList.postValue(AppDataResponse(error))
                    }
                }

                override fun onFailure(call: Call<AppData>, t: Throwable) {
                    Log.e(TAG, "onFailure call="+call.toString()+"\n"+t.message )
                    mutableList.postValue(AppDataResponse(t))
                }

            })


        return mutableList
    }
}