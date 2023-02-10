package com.example.registrocurso.data.model

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

class RegistroUsuarioTecDTO {
    @SerializedName("password")
    @Expose
    var password: String? = null

    @SerializedName("tecnicoUsuario")
    @Expose
    private var tecnicoUsuario: UsuarioTecnico? = null
    var usuarioTecnico: UsuarioTecnico?
        get() = tecnicoUsuario
        set(usuarioTecnico) {
            tecnicoUsuario = usuarioTecnico
        }
}