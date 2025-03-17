package com.assem.onedriveintegration.data.models


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class UserOneDriveResponse(
    @SerializedName("@microsoft.graph.tips")
    @Expose
    var microsoftGraphTips: String? = "",
    @SerializedName("@odata.context")
    @Expose
    var odataContext: String? = "",
    @SerializedName("value")
    @Expose
    var oneDriveData: List<OneDriveData>? = listOf()
)