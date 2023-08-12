package com.nctapplication.model.addrepurchase

import com.google.gson.annotations.SerializedName

data class Addrepurchase (

    @SerializedName("success" ) var success : Boolean? = null,
    @SerializedName("message" ) var message : String?  = null,
    @SerializedName("data"    ) var data    : String?  = null

)