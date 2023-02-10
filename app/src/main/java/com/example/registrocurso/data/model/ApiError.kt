package com.example.registrocurso.data.model

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

class ApiError {
    @SerializedName("Code")
    @Expose
    var code: String? = null

    @SerializedName("Description")
    @Expose
    var description: String? = null
}