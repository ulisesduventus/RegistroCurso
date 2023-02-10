package com.example.registrocurso.data.model


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

class TecEventoPresentacionTotal : ModelBase() {


    @SerializedName("TecEventoConteoClientes")
    @Expose
    var TecEventoConteoClientes = 0

    @SerializedName("TecEventoConteoTecnicos")
    @Expose
    var TecEventoConteoTecnicos = 0

    @SerializedName("TecEventoConteoRepetidos")
    @Expose
    var TecEventoConteoRepetidos = 0

    @SerializedName("TecEventoConteoMaxCupo")
    @Expose
    var TecEventoConteoMaxCupo = 0

    @SerializedName("TecEventoAsistenciaConteoManual")
    @Expose
    var TecEventoAsistenciaConteoManual = 0

    @SerializedName("TecEventoAsistenciaConteoClientes")
    @Expose
    var TecEventoAsistenciaConteoClientes = 0

    @SerializedName("TecEventoAsistenciaConteoTecnicos")
    @Expose
    var TecEventoAsistenciaConteoTecnicos = 0











}