package com.example.registrocurso.data.remote

import android.content.Context


object ApiUtils {

    // PRODUCCION
   // private const val BASE_URL = "https://duventusexternoapi.azurewebsites.net/"


    // PRUEBAS LOCALES
  const val BASE_URL = "http://192.168.12.115/Duventus.Externo.API/"


    fun getAPIService(context: Context?): APIService {
        return RetrofitClient.getClient(BASE_URL, context)!!.create(APIService::class.java)
    }


}