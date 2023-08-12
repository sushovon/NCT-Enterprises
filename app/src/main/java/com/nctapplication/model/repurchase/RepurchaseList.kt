package com.nctapplication.model.repurchase

import com.google.gson.annotations.SerializedName


data class RepurchaseList (

  @SerializedName("member_fname"        ) var memberFname       : String? = null,
  @SerializedName("member_lname"        ) var memberLname       : String? = null,
  @SerializedName("buyer_comission"     ) var buyerComission    : String? = null,
  @SerializedName("quantity"            ) var quantity          : String? = null,
  @SerializedName("total_price"         ) var totalPrice        : String? = null,
  @SerializedName("purchase_created_at" ) var purchaseCreatedAt : String? = null

)