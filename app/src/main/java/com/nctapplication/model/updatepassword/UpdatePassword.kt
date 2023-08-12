package com.nctapplication.model.updatepassword

import com.google.gson.annotations.SerializedName


data class UpdatePassword (

  @SerializedName("success" ) var success : Boolean? = null,
  @SerializedName("message" ) var message : String?  = null,
  @SerializedName("data"    ) var data    : Data?    = Data()

)