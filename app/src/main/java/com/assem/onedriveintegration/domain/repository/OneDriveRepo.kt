package com.assem.onedriveintegration.domain.repository

import com.assem.onedriveintegration.data.models.UserOneDriveResponse
import com.assem.onedriveintegration.utils.Resource

interface OneDriveRepo {
    suspend fun getUserOneDrive(): Resource<UserOneDriveResponse>
}