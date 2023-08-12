package com.nctapplication.model.checkmobile

import com.google.gson.annotations.SerializedName


data class CheckMobile (

  @SerializedName("success" ) var success : Boolean? = null,
  @SerializedName("message" ) var message : String?  = null,
  @SerializedName("data"    ) var data    : Data?    = Data()

)