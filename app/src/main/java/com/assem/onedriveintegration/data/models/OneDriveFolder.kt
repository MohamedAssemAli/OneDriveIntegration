package com.assem.onedriveintegration.data.models


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class OneDriveFolder(
    @SerializedName("childCount")
    @Expose
    var childCount: Int? = null
)