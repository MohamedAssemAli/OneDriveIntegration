package com.assem.onedriveintegration.data.models


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class CreatedBy(
    @SerializedName("application")
    @Expose
    var application: Application? = null,
    @SerializedName("user")
    @Expose
    var user: User? = null
)