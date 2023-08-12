package com.nctapplication.model.directsale

import com.google.gson.annotations.SerializedName


data class Data (

  @SerializedName("directsale_list" ) var directsaleList : ArrayList<DirectsaleList> = arrayListOf()

)