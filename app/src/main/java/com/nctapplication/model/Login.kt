package com.nctapplication.model

import com.google.gson.annotations.SerializedName


data class Login (

  @SerializedName("success" ) var success : Boolean? = null,
  @SerializedName("message" ) var message : String?  = null,
  @SerializedName("data"    ) var data    : Data?    = Data()

)