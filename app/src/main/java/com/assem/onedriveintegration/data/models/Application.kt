package com.assem.onedriveintegration.data.models


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class Application(
    @SerializedName("displayName")
    @Expose
    var displayName: String? = null,
    @SerializedName("id")
    @Expose
    var id: String? = null
)