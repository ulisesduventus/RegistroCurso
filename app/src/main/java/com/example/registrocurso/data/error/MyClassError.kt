package com.example.registrocurso.data.error

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

class MyClassError {
    @SerializedName("error")
    @Expose
    var error: String? = null

    @SerializedName("ExceptionMessage")
    @Expose
    var exceptionMessage: String? = null
}