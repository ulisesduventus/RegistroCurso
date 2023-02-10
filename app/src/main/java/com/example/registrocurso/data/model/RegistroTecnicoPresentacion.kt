package com.example.registrocurso.data.model


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

class RegistroTecnicoPresentacion : ModelBase() {
    @SerializedName("PresentacionId")
    @Expose
    var presentacionId = 0

    @SerializedName("TecnicoId")
    @Expose
    var tecnicoId = 0

    @SerializedName("Estatus")
    @Expose
    var estatus: String? = null

    @SerializedName("Tecnico")
    @Expose
    var tecnico: Any? = null
}