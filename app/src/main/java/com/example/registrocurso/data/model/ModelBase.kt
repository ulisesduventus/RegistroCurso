package com.example.registrocurso.data.model

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose
import java.io.Serializable


open class ModelBase : Serializable {
    @SerializedName("Id")
    @Expose
    var id = 0

    @SerializedName("DateCreated")
    @Expose
    var dateCreated: String? = null

    @SerializedName("DateUpdated")
    @Expose
    var dateUpdated: String? = null

    @SerializedName("IsActive")
    @Expose
    var isActive = false
}