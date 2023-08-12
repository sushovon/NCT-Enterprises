package com.nctapplication.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.example.dashboard.DashboardMember
import com.nctapplication.commons.ApiInterface
import com.nctapplication.model.profile.ProfileData
import com.nctapplication.model.wallet.Wallet
import com.nctapplication.response.WalletApiResponse
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class WalletRepository
@Inject
constructor(private val retroInstance: ApiInterface){
    val TAG = javaClass.simpleName

    fun fetchWallet(requestBody: RequestBody): MutableLiveData<WalletApiResponse> {
        var mutableList: MutableLiveData<WalletApiResponse> = MutableLiveData()

        retroInstance.wallet_details(requestBody).enqueue(object: Callback<Wallet> {

                override fun onResponse(call: Call<Wallet>, response: Response<Wallet>) {


                    if(response.isSuccessful && response.body()!=null){
                        mutableList.postValue(WalletApiResponse(response.body()))
                    } else {
                        val error: Throwable? = null
                        mutableList.postValue(WalletApiResponse(error))
                    }
                }

                override fun onFailure(call: Call<Wallet>, t: Throwable) {
                    Log.e(TAG, "onFailure call="+call.toString()+"\n"+t.message )
                    mutableList.postValue(WalletApiResponse(t))
                }

            })


        return mutableList
    }
}