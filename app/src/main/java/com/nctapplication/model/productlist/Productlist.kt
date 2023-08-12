package com.nctapplication.model.productlist

import com.google.gson.annotations.SerializedName


data class Productlist (

  @SerializedName("success" ) var success : Boolean?        = null,
  @SerializedName("message" ) var message : String?         = null,
  @SerializedName("data"    ) var data    : ArrayList<Data> = arrayListOf()

)