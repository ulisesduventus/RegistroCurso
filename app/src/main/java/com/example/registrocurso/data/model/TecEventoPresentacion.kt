package com.example.registrocurso.data.model


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose


class TecEventoPresentacion : ModelBase() {
    @SerializedName("Fecha_Inicio")
    @Expose
    var fecha_inicio: String? = null

    @SerializedName("Fecha_Fin")
    @Expose
    var fecha_Fin: String? = null

    @SerializedName("Cupo_Minimo")
    @Expose
    var cupo_Minimo = 0

    @SerializedName("Cupo_Maximo")
    @Expose
    var cupo_Maximo = 0

    @SerializedName("Lugar")
    @Expose
    var lugar: String? = null

    @SerializedName("Direccion")
    @Expose
    var direccion: String? = null

    @SerializedName("Ciudad")
    @Expose
    var ciudad: String? = null

    @SerializedName("Estado")
    @Expose
    var estado: String? = null

    @SerializedName("Pais")
    @Expose
    var pais: String? = null

    @SerializedName("Expositor_Principal")
    @Expose
    var expositor_Principal: String? = null

    @SerializedName("Estatus")
    @Expose
    var estatus = 0

    @SerializedName("Hora_inicio")
    @Expose
    var hora_inicio: String? = null

    @SerializedName("Hora_Fin")
    @Expose
    var hora_Fin: String? = null

    @SerializedName("Latitud")
    @Expose
    var latitud: String? = null

    @SerializedName("Longitud")
    @Expose
    var longitud: String? = null

    @SerializedName("Dias_Faltantes")
    @Expose
    var dias_Faltantes = 0

    @SerializedName("Lugares_Disponibles")
    @Expose
    var lugares_Disponibles = 0

    @SerializedName("Horas_Evento")
    @Expose
    var horas_Evento: String? = null

    @SerializedName("Fecha_Inicios_String")
    @Expose
    var fecha_Inicios_String: String? = null

    @SerializedName("Fecha_Fin_String")
    @Expose
    var fecha_Fin_String: String? = null

    @SerializedName("Hora_Inicios_String")
    @Expose
    var hora_Inicios_String: String? = null

    @SerializedName("RegistrosTecnicos")
    @Expose
    var registrosTecnicos: ArrayList<RegistroTecnicoPresentacion>? = null

    @SerializedName("Extended")
    @Expose
    var isExpanded = false

    @SerializedName("IsVideoConferencia")
    @Expose
    var isVideoConferencia = false

    @SerializedName("URLVideoConferencia")
    @Expose
    var uRLVideoConferencia: String? = null
}