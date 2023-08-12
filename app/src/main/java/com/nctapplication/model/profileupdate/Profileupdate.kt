package com.nctapplication.model.profileupdate

import com.google.gson.annotations.SerializedName


data class Profileupdate (

  @SerializedName("success" ) var success : Boolean? = null,
  @SerializedName("message" ) var message : String?  = null,
  @SerializedName("data"    ) var data    : Data?    = Data()

)