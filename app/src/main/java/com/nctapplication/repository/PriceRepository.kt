package com.nctapplication.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.example.dashboard.DashboardMember
import com.nctapplication.commons.ApiInterface
import com.nctapplication.model.checkmobile.CheckMobile
import com.nctapplication.model.claim.Claim
import com.nctapplication.model.memberaward.Member
import com.nctapplication.model.price.Price
import com.nctapplication.response.PriceResponse
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class PriceRepository @Inject constructor(private val retroInstance: ApiInterface){
    val TAG = javaClass.simpleName

    fun price(): MutableLiveData<PriceResponse> {
        var mutableList: MutableLiveData<PriceResponse> = MutableLiveData()

        retroInstance.price().enqueue(object: Callback<Price> {

                override fun onResponse(call: Call<Price>, response: Response<Price>) {


                    if(response.isSuccessful && response.body()!=null){
                        mutableList.postValue(PriceResponse(response.body()))
                    } else {
                        val error: Throwable? = null
                        mutableList.postValue(PriceResponse(error))
                    }
                }

                override fun onFailure(call: Call<Price>, t: Throwable) {
                    Log.e(TAG, "onFailure call="+call.toString()+"\n"+t.message )
                    mutableList.postValue(PriceResponse(t))
                }

            })


        return mutableList
    }
}