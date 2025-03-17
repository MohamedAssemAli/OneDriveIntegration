package com.assem.onedriveintegration.data.models


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class OneDriveData(
    @SerializedName("cTag")
    @Expose
    var cTag: String? = "",
    @SerializedName("createdBy")
    @Expose
    var createdBy: CreatedBy? = CreatedBy(),
    @SerializedName("createdDateTime")
    @Expose
    var createdDateTime: String? = "",
    @SerializedName("eTag")
    @Expose
    var eTag: String? = "",
    @SerializedName("file")
    @Expose
    var oneDriveFile: OneDriveFile? = OneDriveFile(),
    @SerializedName("fileSystemInfo")
    @Expose
    var fileSystemInfo: FileSystemInfo? = FileSystemInfo(),
    @SerializedName("folder")
    @Expose
    var oneDriveFolder: OneDriveFolder? = OneDriveFolder(),
    @SerializedName("id")
    @Expose
    var id: String? = "",
    @SerializedName("lastModifiedBy")
    @Expose
    var lastModifiedBy: LastModifiedBy? = LastModifiedBy(),
    @SerializedName("lastModifiedDateTime")
    @Expose
    var lastModifiedDateTime: String? = "",
    @SerializedName("@microsoft.graph.downloadUrl")
    @Expose
    var microsoftGraphDownloadUrl: String? = "",
    @SerializedName("name")
    @Expose
    var name: String? = "",
    @SerializedName("parentReference")
    @Expose
    var parentReference: ParentReference? = ParentReference(),
    @SerializedName("shared")
    @Expose
    var shared: Shared? = Shared(),
    @SerializedName("size")
    @Expose
    var size: Long? = 0,
    @SerializedName("specialFolder")
    @Expose
    var specialFolder: SpecialFolder? = SpecialFolder(),
    @SerializedName("webUrl")
    @Expose
    var webUrl: String? = ""
)