package com.assem.onedriveintegration.domain.entity

data class OneDriveItem(
    var createdDateTime: String = "",
    var downloadUrl: String = "",
    var name: String = "",
    var extension: String = "",
    var size: String = "",
    var type: OneDriveItemType = OneDriveItemType.FILE,
)