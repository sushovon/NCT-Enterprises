package com.nctapplication.model.transactiondetails

import com.google.gson.annotations.SerializedName


data class TransactionDetails (

  @SerializedName("success" ) var success : Boolean?        = null,
  @SerializedName("message" ) var message : String?         = null,
  @SerializedName("data"    ) var data    : ArrayList<Data> = arrayListOf()

)