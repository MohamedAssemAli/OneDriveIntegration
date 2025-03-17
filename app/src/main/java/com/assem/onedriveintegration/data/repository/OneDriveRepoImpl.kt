package com.assem.onedriveintegration.data.repository

import com.assem.onedriveintegration.data.mapper.toDomain
import com.assem.onedriveintegration.data.models.UserOneDriveResponse
import com.assem.onedriveintegration.data.remote.OneDriveApi
import com.assem.onedriveintegration.domain.entity.OneDriveItem
import com.assem.onedriveintegration.domain.repository.OneDriveRepo
import com.assem.onedriveintegration.utils.Resource
import javax.inject.Inject

class OneDriveRepoImpl @Inject constructor(
    private val api: OneDriveApi,
) : OneDriveRepo {

    override suspend fun getUserOneDrive(): Resource<UserOneDriveResponse> {
        return  safeApiCall { api.getUserOneDrive() }

        /*

        val response = safeApiCall { api.getUserOneDrive() }
        when (response) {
            is Resource.Error -> {
                return Resource.Error(response.error ?: "Something wrong happened!")
            }

            is Resource.Loading -> {
                return Resource.Loading()
            }

            is Resource.Success -> {
                val data = response.data?.oneDriveData?.map { it.toDomain() }
                return Resource.Success(data)
            }
        }
         */
    }
}