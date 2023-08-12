package com.nctapplication.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.example.dashboard.DashboardMember
import com.nctapplication.commons.ApiInterface
import com.nctapplication.model.addrepurchase.Addrepurchase
import com.nctapplication.model.checkmobile.CheckMobile
import com.nctapplication.model.claim.Claim
import com.nctapplication.model.memberaward.Member
import com.nctapplication.response.AddrepurchaseResponse
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class AddrepurchaseRepository @Inject constructor(private val retroInstance: ApiInterface){
    val TAG = javaClass.simpleName

    fun add_repurchase(requestBody: RequestBody): MutableLiveData<AddrepurchaseResponse> {
        var mutableList: MutableLiveData<AddrepurchaseResponse> = MutableLiveData()

        retroInstance.add_repurchase(requestBody).enqueue(object: Callback<Addrepurchase> {

                override fun onResponse(call: Call<Addrepurchase>, response: Response<Addrepurchase>) {


                    if(response.isSuccessful && response.body()!=null){
                        mutableList.postValue(AddrepurchaseResponse(response.body()))
                    } else {
                        val error: Throwable? = null
                        mutableList.postValue(AddrepurchaseResponse(error))
                    }
                }

                override fun onFailure(call: Call<Addrepurchase>, t: Throwable) {
                    Log.e(TAG, "onFailure call="+call.toString()+"\n"+t.message )
                    mutableList.postValue(AddrepurchaseResponse(t))
                }

            })


        return mutableList
    }
}