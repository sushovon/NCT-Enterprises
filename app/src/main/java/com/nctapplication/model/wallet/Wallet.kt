package com.nctapplication.model.wallet

import com.google.gson.annotations.SerializedName


data class Wallet (

  @SerializedName("success" ) var success : Boolean? = null,
  @SerializedName("message" ) var message : String?  = null,
  @SerializedName("data"    ) var data    : Data?    = Data()

)