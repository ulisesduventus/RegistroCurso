package com.example.registrocurso.data.remote

import android.content.Context
import retrofit2.Retrofit
import okhttp3.OkHttpClient
import com.example.registrocurso.data.error.NetworkConnectionInterceptor
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    private var retrofit: Retrofit? = null
    private var retrofit2: Retrofit? = null
    fun getClient(baseUrl: String?, mContext: Context?): Retrofit? {
        if (retrofit == null) {
            val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(90, TimeUnit.SECONDS)
                .writeTimeout(90, TimeUnit.SECONDS)
                .readTimeout(90, TimeUnit.SECONDS)
                .addInterceptor(NetworkConnectionInterceptor(mContext!!))
                .build()
            retrofit = Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(baseUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit
    }

    fun getClient2(baseUrl: String?): Retrofit? {
        if (retrofit2 == null) {
            val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(90, TimeUnit.SECONDS)
                .writeTimeout(90, TimeUnit.SECONDS)
                .readTimeout(90, TimeUnit.SECONDS)
                .build()
            retrofit2 = Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(baseUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit2
    }
}