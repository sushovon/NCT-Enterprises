package com.nctapplication.model.couponprice

import com.google.gson.annotations.SerializedName

data class CouponPrice (

    @SerializedName("success" ) var success : Boolean? = null,
    @SerializedName("message" ) var message : String?  = null,
    @SerializedName("data"    ) var data    : Data?    = Data()

)