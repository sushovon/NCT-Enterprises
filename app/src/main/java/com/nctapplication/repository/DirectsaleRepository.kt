package com.nctapplication.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.example.dashboard.DashboardMember
import com.nctapplication.commons.ApiInterface
import com.nctapplication.model.directsale.Directsale
import com.nctapplication.model.profile.ProfileData
import com.nctapplication.model.repurchase.Repurchase
import com.nctapplication.model.wallet.Wallet
import com.nctapplication.response.DirectsaleResponse
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class DirectsaleRepository
@Inject
constructor(private val retroInstance: ApiInterface){
    val TAG = javaClass.simpleName

    fun fetchDirectsale(requestBody: RequestBody): MutableLiveData<DirectsaleResponse> {
        var mutableList: MutableLiveData<DirectsaleResponse> = MutableLiveData()

        retroInstance.directsale(requestBody).enqueue(object: Callback<Directsale> {

                override fun onResponse(call: Call<Directsale>, response: Response<Directsale>) {


                    if(response.isSuccessful && response.body()!=null){
                        mutableList.postValue(DirectsaleResponse(response.body()))
                    } else {
                        val error: Throwable? = null
                        mutableList.postValue(DirectsaleResponse(error))
                    }
                }

                override fun onFailure(call: Call<Directsale>, t: Throwable) {
                    Log.e(TAG, "onFailure call="+call.toString()+"\n"+t.message )
                    mutableList.postValue(DirectsaleResponse(t))
                }

            })


        return mutableList
    }
}