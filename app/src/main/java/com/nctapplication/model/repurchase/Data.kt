package com.nctapplication.model.repurchase

import com.google.gson.annotations.SerializedName


data class Data (

  @SerializedName("repurchase_list" ) var repurchaseList : ArrayList<RepurchaseList> = arrayListOf()

)