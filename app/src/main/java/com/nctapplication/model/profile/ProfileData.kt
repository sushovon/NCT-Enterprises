package com.nctapplication.model.profile

import com.google.gson.annotations.SerializedName


data class ProfileData (

  @SerializedName("success" ) var success : Boolean?        = null,
  @SerializedName("message" ) var message : String?         = null,
  @SerializedName("data"    ) var data    : ArrayList<Data> = arrayListOf()

)