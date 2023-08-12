package com.nctapplication.model.directsale

import com.google.gson.annotations.SerializedName


data class Directsale (

  @SerializedName("success" ) var success : Boolean? = null,
  @SerializedName("message" ) var message : String?  = null,
  @SerializedName("data"    ) var data    : Data?    = Data()

)