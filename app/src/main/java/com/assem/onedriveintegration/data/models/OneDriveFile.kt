package com.assem.onedriveintegration.data.models


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class OneDriveFile(
    @SerializedName("hashes")
    @Expose
    var hashes: Hashes? = null,
    @SerializedName("mimeType")
    @Expose
    var mimeType: String? = null
)