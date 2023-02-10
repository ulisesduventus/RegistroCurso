package com.example.registrocurso.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*

class UsuarioTecnico {
    @Expose
    @SerializedName("Empresa")
    var empresa: String? = null

    @Expose
    @SerializedName("Company")
    var company: String? = null

    @Expose
    @SerializedName("Celular")
    var celular: String? = null

    @Expose
    @SerializedName("Telefono")
    var telefono: String? = null

    @Expose
    @SerializedName("Foto")
    var foto: String? = null

    @Expose
    @SerializedName("IdCAD")
    var idCAD: String? = null

    @Expose
    @SerializedName("FechaNacimiento")
    var fechaNacimiento: String? = null

    @Expose
    @SerializedName("Id")
    var id = 0

    @Expose
    @SerializedName("IsActive")
    var isActive = false

    @Expose
    @SerializedName("User")
    var user: User? = null

    @Expose
    @SerializedName("UserId")
    var userId: String? = null

    @Expose
    @SerializedName("Ciudad")
    var city: String? = null
        get() = field
        set

    @Expose
    @SerializedName("Estado")
    var estado: String? = null

    @Expose
    @SerializedName("Pais")
    var pais: String? = null

    @Expose
    @SerializedName("IsCertificado")
    var isCertificado = false

    @Expose
    @SerializedName("GuidId")
    var guidId: UUID? = null
    fun getIsActive(): Boolean {
        return isActive
    }

    fun setIsActive(isActive: Boolean) {
        this.isActive = isActive
    }
}