package com.assem.onedriveintegration.data.mapper

import com.assem.onedriveintegration.data.models.OneDriveData
import com.assem.onedriveintegration.domain.entity.OneDriveItem
import com.assem.onedriveintegration.domain.entity.OneDriveItemType
import com.assem.onedriveintegration.utils.formatFileSize

fun OneDriveData.toDomain(): OneDriveItem {
    return OneDriveItem(
        createdDateTime = createdDateTime.orEmpty(),
        downloadUrl = microsoftGraphDownloadUrl.orEmpty(),
        name = name.orEmpty(),
        extension = name?.substringAfterLast(".") ?: "",
        size = size?.formatFileSize() ?: "",
        type = if (oneDriveFolder != null) OneDriveItemType.FOLDER else OneDriveItemType.FILE
    )
}