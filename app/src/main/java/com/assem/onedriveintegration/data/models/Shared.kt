package com.assem.onedriveintegration.data.models


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class Shared(
    @SerializedName("scope")
    @Expose
    var scope: String? = null
)