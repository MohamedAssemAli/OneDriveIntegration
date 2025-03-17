package com.assem.onedriveintegration.data.models


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class SpecialFolder(
    @SerializedName("name")
    @Expose
    var name: String? = null
)