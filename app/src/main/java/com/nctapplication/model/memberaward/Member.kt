package com.nctapplication.model.memberaward

import com.google.gson.annotations.SerializedName


data class Member (

  @SerializedName("success" ) var success : Boolean? = null,
  @SerializedName("message" ) var message : String?  = null,
  @SerializedName("data"    ) var data    : Data?    = Data()

)