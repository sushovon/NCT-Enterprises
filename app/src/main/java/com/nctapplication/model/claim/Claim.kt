package com.nctapplication.model.claim

import com.google.gson.annotations.SerializedName


data class Claim (

  @SerializedName("success" ) var success : Boolean? = null,
  @SerializedName("message" ) var message : String?  = null,
  @SerializedName("data"    ) var data    : Data?    = Data()

)