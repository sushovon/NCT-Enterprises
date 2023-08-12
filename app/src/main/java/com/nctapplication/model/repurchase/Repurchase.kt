package com.nctapplication.model.repurchase

import com.google.gson.annotations.SerializedName


data class Repurchase (

  @SerializedName("success" ) var success : Boolean? = null,
  @SerializedName("message" ) var message : String?  = null,
  @SerializedName("data"    ) var data    : Data?    = Data()

)