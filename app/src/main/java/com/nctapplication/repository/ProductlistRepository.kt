package com.nctapplication.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.example.dashboard.DashboardMember
import com.nctapplication.commons.ApiInterface
import com.nctapplication.model.checkmobile.CheckMobile
import com.nctapplication.model.claim.Claim
import com.nctapplication.model.memberaward.Member
import com.nctapplication.model.productlist.Productlist
import com.nctapplication.response.ProductlistResponse
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class ProductlistRepository
@Inject
constructor(private val retroInstance: ApiInterface){
    val TAG = javaClass.simpleName

    fun product_category(): MutableLiveData<ProductlistResponse> {
        var mutableList: MutableLiveData<ProductlistResponse> = MutableLiveData()

        retroInstance.product_category().enqueue(object: Callback<Productlist> {

                override fun onResponse(call: Call<Productlist>, response: Response<Productlist>) {


                    if(response.isSuccessful && response.body()!=null){
                        mutableList.postValue(ProductlistResponse(response.body()))
                    } else {
                        val error: Throwable? = null
                        mutableList.postValue(ProductlistResponse(error))
                    }
                }

                override fun onFailure(call: Call<Productlist>, t: Throwable) {
                    Log.e(TAG, "onFailure call="+call.toString()+"\n"+t.message )
                    mutableList.postValue(ProductlistResponse(t))
                }

            })


        return mutableList
    }
}