package com.assem.onedriveintegration.data.models


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class LastModifiedBy(
    @SerializedName("application")
    @Expose
    var application: Application? = Application(),
    @SerializedName("user")
    @Expose
    var user: User? = User()
)