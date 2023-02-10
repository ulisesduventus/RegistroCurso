package com.example.registrocurso.data.model


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose


class TecEvento : ModelBase() {
    @SerializedName("Nombre")
    @Expose
    var nombre: String? = null

    @SerializedName("Descripcion")
    @Expose
    var descripcion: String? = null

    @SerializedName("Imagen")
    @Expose
    var imagen: String? = null

    @SerializedName("Video1")
    @Expose
    var video1: String? = null

    @SerializedName("Video2")
    @Expose
    var video2: String? = null

    @SerializedName("Video3")
    @Expose
    var video3: String? = null

    @SerializedName("Objetivos")
    @Expose
    var objetivos: String? = null

    @SerializedName("Alcance")
    @Expose
    var alcance: String? = null

    @SerializedName("Temario")
    @Expose
    var temario: String? = null

    @SerializedName("TecEventoType")
    @Expose
    var tecEventoType: TecEventoType? = null

    @SerializedName("Presentaciones")
    @Expose
    var presentaciones: ArrayList<TecEventoPresentacion>? = null

    @SerializedName("Extended")
    @Expose
    var isExpanded = false
}