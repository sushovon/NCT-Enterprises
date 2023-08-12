package com.nctapplication.model.productlist

import com.google.gson.annotations.SerializedName


data class Data (

  @SerializedName("product_category_id" ) var productCategoryId : String? = null,
  @SerializedName("product_category"    ) var productCategory   : String? = null,
  @SerializedName("priority"            ) var priority          : String? = null,
  @SerializedName("status"              ) var status            : String? = null

)