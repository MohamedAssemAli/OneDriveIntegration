package com.assem.onedriveintegration.data.models


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class Hashes(
    @SerializedName("quickXorHash")
    @Expose
    var quickXorHash: String? = null
)