package com.nctapplication.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.nctapplication.commons.ApiInterface
import com.nctapplication.commons.MyApp
import com.nctapplication.model.Login
import com.nctapplication.response.ApiResponse
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginRepository {
    val TAG = javaClass.simpleName

    fun fetchLogin(requestBody: RequestBody): MutableLiveData<ApiResponse> {
        var mutableList: MutableLiveData<ApiResponse> = MutableLiveData()

        //val apiInterface = MyApplication.getRetrofitClient().create(ApiInterface::class.java)
        val apiInterface= MyApp.getRetrofitClient()?.create(ApiInterface::class.java)
        if (apiInterface != null) {
            apiInterface.login(requestBody).enqueue(object: Callback<Login> {

                override fun onResponse(call: Call<Login>, response: Response<Login>) {
                    //Log.e(TAG, "onResponse response="+response.toString() )

                    if(response.isSuccessful && response.body()!=null){
                        mutableList.postValue(ApiResponse(response.body()))
                    } else {
                        val error: Throwable? = null
                        mutableList.postValue(ApiResponse(error))
                    }
                }

                override fun onFailure(call: Call<Login>, t: Throwable) {
                    Log.e(TAG, "onFailure call="+call.toString()+"\n"+t.message )
                    mutableList.postValue(ApiResponse(t))
                }

            })
        }

        return mutableList
    }
}