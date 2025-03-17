package com.assem.onedriveintegration.data.models


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("error")
    @Expose
    var error: Error? = null
)


data class Error(
    @SerializedName("code")
    @Expose
    var code: String? = null,
    @SerializedName("innerError")
    @Expose
    var innerError: InnerError? = null,
    @SerializedName("message")
    @Expose
    var message: String? = null
)


data class InnerError(
    @SerializedName("client-request-id")
    @Expose
    var clientRequestId: String? = null,
    @SerializedName("date")
    @Expose
    var date: String? = null,
    @SerializedName("request-id")
    @Expose
    var requestId: String? = null
)