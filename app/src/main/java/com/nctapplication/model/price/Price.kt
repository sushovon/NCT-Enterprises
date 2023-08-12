package com.nctapplication.model.price

import com.google.gson.annotations.SerializedName


data class Price (

  @SerializedName("success" ) var success : Boolean?        = null,
  @SerializedName("message" ) var message : String?         = null,
  @SerializedName("data"    ) var data    : ArrayList<Data> = arrayListOf()

)