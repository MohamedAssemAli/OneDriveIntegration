package com.assem.onedriveintegration.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assem.onedriveintegration.data.mapper.toDomain
import com.assem.onedriveintegration.domain.entity.OneDriveItem
import com.assem.onedriveintegration.domain.repository.OneDriveRepo
import com.assem.onedriveintegration.utils.Resource
import com.assem.onedriveintegration.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class OneDriveViewModel @Inject constructor(
    private val oneDriveRepo: OneDriveRepo,
) : ViewModel() {

    private val _userOneDriveFiles: SingleLiveEvent<Resource<List<OneDriveItem>>> =
        SingleLiveEvent()
    val userOneDriveFiles: SingleLiveEvent<Resource<List<OneDriveItem>>> = _userOneDriveFiles

    fun getUserFiles() = viewModelScope.launch {
        when (val response = oneDriveRepo.getUserOneDrive()) {
            is Resource.Error -> {
                _userOneDriveFiles.value =
                    Resource.Error(response.error ?: "Something wrong happened!")
            }

            is Resource.Loading -> {
                _userOneDriveFiles.value = Resource.Loading()
            }

            is Resource.Success -> {
                val data = response.data?.oneDriveData?.map { it.toDomain() }
                _userOneDriveFiles.value = Resource.Success(data)
            }
        }
    }
}