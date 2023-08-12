package com.nctapplication.model.profileupdate

import com.google.gson.annotations.SerializedName


data class Data (

  @SerializedName("member_id"          ) var memberId         : String? = null,
  @SerializedName("referral_code"      ) var referralCode     : String? = null,
  @SerializedName("referral_parent_id" ) var referralParentId : String? = null,
  @SerializedName("member_fname"       ) var memberFname      : String? = null,
  @SerializedName("member_lname"       ) var memberLname      : String? = null,
  @SerializedName("member_email"       ) var memberEmail      : String? = null,
  @SerializedName("member_password"    ) var memberPassword   : String? = null,
  @SerializedName("member_phoneno"     ) var memberPhoneno    : String? = null,
  @SerializedName("member_image"       ) var memberImage      : String? = null,
  @SerializedName("member_aadharcard"  ) var memberAadharcard : String? = null,
  @SerializedName("member_pancard"     ) var memberPancard    : String? = null,
  @SerializedName("aadharcard_file"    ) var aadharcardFile   : String? = null,
  @SerializedName("pancard_file"       ) var pancardFile      : String? = null,
  @SerializedName("user_id"            ) var userId           : String? = null,
  @SerializedName("member_created_at"  ) var memberCreatedAt  : String? = null,
  @SerializedName("member_updated_at"  ) var memberUpdatedAt  : String? = null,
  @SerializedName("member_status"      ) var memberStatus     : String? = null,
  @SerializedName("member_deleted"     ) var memberDeleted    : String? = null

)