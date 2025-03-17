package com.assem.onedriveintegration.data.models


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class ParentReference(
    @SerializedName("driveId")
    @Expose
    var driveId: String? = null,
    @SerializedName("driveType")
    @Expose
    var driveType: String? = null,
    @SerializedName("id")
    @Expose
    var id: String? = null,
    @SerializedName("name")
    @Expose
    var name: String? = null,
    @SerializedName("path")
    @Expose
    var path: String? = null,
    @SerializedName("siteId")
    @Expose
    var siteId: String? = null
)