package com.nctapplication.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.example.dashboard.DashboardMember
import com.nctapplication.commons.ApiInterface
import com.nctapplication.model.claim.Claim
import com.nctapplication.model.memberaward.Member
import com.nctapplication.model.payout.Payout
import com.nctapplication.response.PayoutResponse
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class PayoutRepository @Inject constructor(private val retroInstance: ApiInterface){
    val TAG = javaClass.simpleName

    fun fetchPayout(requestBody: RequestBody): MutableLiveData<PayoutResponse> {
        var mutableList: MutableLiveData<PayoutResponse> = MutableLiveData()

        retroInstance.payout(requestBody).enqueue(object: Callback<Payout> {

                override fun onResponse(call: Call<Payout>, response: Response<Payout>) {


                    if(response.isSuccessful && response.body()!=null){
                        mutableList.postValue(PayoutResponse(response.body()))
                    } else {
                        val error: Throwable? = null
                        mutableList.postValue(PayoutResponse(error))
                    }
                }

                override fun onFailure(call: Call<Payout>, t: Throwable) {
                    Log.e(TAG, "onFailure call="+call.toString()+"\n"+t.message )
                    mutableList.postValue(PayoutResponse(t))
                }

            })


        return mutableList
    }
}