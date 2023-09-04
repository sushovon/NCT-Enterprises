package com.nctapplication.model.deletemember

import com.google.gson.annotations.SerializedName


data class Memberdelete (

    @SerializedName("success" ) var success : Boolean? = null,
    @SerializedName("message" ) var message : String?  = null,
    @SerializedName("data"    ) var data    : Data?    = Data()

)