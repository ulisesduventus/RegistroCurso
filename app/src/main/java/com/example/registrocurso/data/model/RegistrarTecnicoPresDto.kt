package com.example.registrocurso.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class RegistrarTecnicoPresDto {
    @SerializedName("PresentacionId")
    @Expose
    var presentacionId = 0

    @SerializedName("TecnicoId")
    @Expose
    var tecnicoId = 0


    @SerializedName("TokenKey")
    @Expose
    var tokenKey: String? = null

    @SerializedName("TecnicoUserID")
    @Expose
    var tecnicoUserID: String? = null


}