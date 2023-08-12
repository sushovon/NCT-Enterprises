package com.nctapplication.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.nctapplication.commons.ApiInterface
import com.nctapplication.model.couponprice.CouponPrice
import com.nctapplication.response.CouponPriceResponse
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class CouponPriceRepository @Inject constructor(private val retroInstance: ApiInterface){
    val TAG = javaClass.simpleName

    fun coupon_price(requestBody: RequestBody): MutableLiveData<CouponPriceResponse> {
        var mutableList: MutableLiveData<CouponPriceResponse> = MutableLiveData()

        retroInstance.coupon_price(requestBody).enqueue(object: Callback<CouponPrice> {

            override fun onResponse(call: Call<CouponPrice>, response: Response<CouponPrice>) {


                if(response.isSuccessful && response.body()!=null){
                    mutableList.postValue(CouponPriceResponse(response.body()))
                } else {
                    val error: Throwable? = null
                    mutableList.postValue(CouponPriceResponse(error))
                }
            }

            override fun onFailure(call: Call<CouponPrice>, t: Throwable) {
                Log.e(TAG, "onFailure call="+call.toString()+"\n"+t.message )
                mutableList.postValue(CouponPriceResponse(t))
            }

        })


        return mutableList
    }
}