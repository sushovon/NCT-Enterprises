package com.nctapplication.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.example.dashboard.DashboardMember
import com.nctapplication.commons.ApiInterface
import com.nctapplication.response.DashboardApiResponse
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class DashboardRepository @Inject constructor(private val retroInstance: ApiInterface){
    val TAG = javaClass.simpleName

    fun fetchDashboard(requestBody: RequestBody): MutableLiveData<DashboardApiResponse> {
        var mutableList: MutableLiveData<DashboardApiResponse> = MutableLiveData()

        retroInstance.memberdashboard(requestBody).enqueue(object: Callback<DashboardMember> {

                override fun onResponse(call: Call<DashboardMember>, response: Response<DashboardMember>) {


                    if(response.isSuccessful && response.body()!=null){
                        mutableList.postValue(DashboardApiResponse(response.body()))
                    } else {
                        val error: Throwable? = null
                        mutableList.postValue(DashboardApiResponse(error))
                    }
                }

                override fun onFailure(call: Call<DashboardMember>, t: Throwable) {
                    Log.e(TAG, "onFailure call="+call.toString()+"\n"+t.message )
                    mutableList.postValue(DashboardApiResponse(t))
                }

            })


        return mutableList
    }
}