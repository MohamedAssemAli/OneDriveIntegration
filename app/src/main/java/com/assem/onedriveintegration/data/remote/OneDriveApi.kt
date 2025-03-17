package com.assem.onedriveintegration.data.remote

import com.assem.onedriveintegration.data.models.UserOneDriveResponse
import retrofit2.Response
import retrofit2.http.GET

interface OneDriveApi {

    @GET("me/drive/root/children")
    suspend fun getUserOneDrive(): Response<UserOneDriveResponse>
}