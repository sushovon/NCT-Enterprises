package com.nctapplication.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.example.dashboard.DashboardMember
import com.nctapplication.commons.ApiInterface
import com.nctapplication.model.profile.ProfileData
import com.nctapplication.model.repurchase.Repurchase
import com.nctapplication.model.wallet.Wallet
import com.nctapplication.response.RepurchaseApiResponse
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class RepurchaseRepository
@Inject
constructor(private val retroInstance: ApiInterface){
    val TAG = javaClass.simpleName

    fun fetchRepurchase(requestBody: RequestBody): MutableLiveData<RepurchaseApiResponse> {
        var mutableList: MutableLiveData<RepurchaseApiResponse> = MutableLiveData()

        retroInstance.repurchase(requestBody).enqueue(object: Callback<Repurchase> {

                override fun onResponse(call: Call<Repurchase>, response: Response<Repurchase>) {


                    if(response.isSuccessful && response.body()!=null){
                        mutableList.postValue(RepurchaseApiResponse(response.body()))
                    } else {
                        val error: Throwable? = null
                        mutableList.postValue(RepurchaseApiResponse(error))
                    }
                }

                override fun onFailure(call: Call<Repurchase>, t: Throwable) {
                    Log.e(TAG, "onFailure call="+call.toString()+"\n"+t.message )
                    mutableList.postValue(RepurchaseApiResponse(t))
                }

            })


        return mutableList
    }
}