package com.example.registrocurso.data.remote

import com.example.registrocurso.data.model.RegistrarTecnicoPresDto
import com.example.registrocurso.data.model.ApiResponse
import com.example.registrocurso.data.model.TecEvento
import com.example.registrocurso.data.model.TecEventoPresentacionTotal
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface APIService {

    @get:GET("api/TecEvento/GetAll")
    val getAllTecEventos: Call<ApiResponse<ArrayList<TecEvento?>?>?>?


    @POST("api/TecEvento/RegistrarTecnicoAll")
    fun RegistrarTecnicoPresentacion(
        @Body registro: RegistrarTecnicoPresDto?
    ): Call<ApiResponse<Boolean?>?>?

    @POST("api/TecnicoUsuario/GuardarTecnicoEventoManualAll")
    fun GuardarTecnicoEventoManual(
        @Body body: RequestBody?

    ): Call<ApiResponse<Boolean?>?>?


    @POST("api/TecEvento/GetAllEventMaxCount")
    fun getMaxConteoRegistros(
       // @Header("Authorization") token: String?,
        @Body body: RequestBody?
    ): Call<ApiResponse<ArrayList<TecEventoPresentacionTotal?>?>?>? // JsonObject


}