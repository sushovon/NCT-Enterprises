package com.nctapplication.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.example.dashboard.DashboardMember
import com.nctapplication.commons.ApiInterface
import com.nctapplication.model.addrepurchase.Addrepurchase
import com.nctapplication.model.checkmobile.CheckMobile
import com.nctapplication.model.claim.Claim
import com.nctapplication.model.memberaward.Member
import com.nctapplication.model.transactiondetails.TransactionDetails
import com.nctapplication.response.TransactionDetailsResponse
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class TransactionDetailsRepository
@Inject
constructor(private val retroInstance: ApiInterface){
    val TAG = javaClass.simpleName

    fun insert_transactiondtls(requestBody: RequestBody): MutableLiveData<TransactionDetailsResponse> {
        var mutableList: MutableLiveData<TransactionDetailsResponse> = MutableLiveData()

        retroInstance.insert_transactiondtls(requestBody).enqueue(object: Callback<TransactionDetails> {

                override fun onResponse(call: Call<TransactionDetails>, response: Response<TransactionDetails>) {


                    if(response.isSuccessful && response.body()!=null){
                        mutableList.postValue(TransactionDetailsResponse(response.body()))
                    } else {
                        val error: Throwable? = null
                        mutableList.postValue(TransactionDetailsResponse(error))
                    }
                }

                override fun onFailure(call: Call<TransactionDetails>, t: Throwable) {
                    Log.e(TAG, "onFailure call="+call.toString()+"\n"+t.message )
                    mutableList.postValue(TransactionDetailsResponse(t))
                }

            })


        return mutableList
    }
}