package com.nctapplication.model.register

import com.google.gson.annotations.SerializedName


data class Register (

  @SerializedName("success" ) var success : Boolean? = null,
  @SerializedName("message" ) var message : String?  = null,
  @SerializedName("data"    ) var data    : Data?    = Data()

)