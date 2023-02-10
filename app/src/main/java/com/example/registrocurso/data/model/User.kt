package com.example.registrocurso.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class User {
    @Expose
    @SerializedName("FirstName")
    var firstName: String? = null

    @Expose
    @SerializedName("LastName")
    var lastName: String? = null

    @Expose
    @SerializedName("Email")
    var email: String? = null

    @Expose
    @SerializedName("Telephone")
    var telephone: String? = null

    @Expose
    @SerializedName("ImagePath")
    var imagePath: String? = null

    @Expose
    @SerializedName("FullName")
    var fullName: String? = null

    @Expose
    @SerializedName("AspNetUserID")
    var aspNetUserID: String? = null

    @Expose
    @SerializedName("Status")
    var status: String? = null

    @Expose
    @SerializedName("registerUser")
    var registerUser: String? = null
}