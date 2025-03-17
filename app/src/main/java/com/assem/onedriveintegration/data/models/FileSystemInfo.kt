package com.assem.onedriveintegration.data.models


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class FileSystemInfo(
    @SerializedName("createdDateTime")
    @Expose
    var createdDateTime: String? = null,
    @SerializedName("lastModifiedDateTime")
    @Expose
    var lastModifiedDateTime: String? = null
)