package com.example.registrocurso.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ApiResponse<T> {
    @Expose
    @SerializedName("Succeeded")
    var isSucceeded = false

    @Expose
    @SerializedName("Error")
    var error: ApiError? = null

    // This throws the exception
    @Expose
    @SerializedName("Data")
    var data: T? = null
        private set

    fun setData(data: T) {
        this.data = data
    }
}