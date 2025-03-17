package com.assem.onedriveintegration.data.models


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class User(
    @SerializedName("displayName")
    @Expose
    var displayName: String? = null,
    @SerializedName("email")
    @Expose
    var email: String? = null,
    @SerializedName("id")
    @Expose
    var id: String? = null
)