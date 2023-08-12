package com.nctapplication.model.appdata

import com.google.gson.annotations.SerializedName


data class AppData (

  @SerializedName("success" ) var success : Boolean?        = null,
  @SerializedName("message" ) var message : String?         = null,
  @SerializedName("data"    ) var data    : ArrayList<Data> = arrayListOf()

)