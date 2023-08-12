package com.nctapplication.model.payout

import com.google.gson.annotations.SerializedName


data class Payout (

  @SerializedName("success" ) var success : Boolean? = null,
  @SerializedName("message" ) var message : String?  = null,
  @SerializedName("data"    ) var data    : Data?    = Data()

)