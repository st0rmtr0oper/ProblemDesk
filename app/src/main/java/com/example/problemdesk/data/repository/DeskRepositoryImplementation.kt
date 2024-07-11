package com.example.problemdesk.data.repository

import com.example.problemdesk.data.datasource.DeskApi
import com.example.problemdesk.domain.repository.DeskRepository
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class DeskRepositoryImplementation: DeskRepository {
    companion object {
        const val BASE_URL = "http://34.141.180.59:443/docs/"
//            "http://158.160.66.207:8000/docs#/default/send_message_endpoint_send_message_post"
    }


    private val gson = GsonBuilder().create()

    private val retrofit = Retrofit.Builder()
        .client(provideOkHttpClient())
        .baseUrl("$BASE_URL/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    private fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient().newBuilder().connectTimeout(10L, TimeUnit.SECONDS)
            .writeTimeout(10L, TimeUnit.SECONDS).readTimeout(10L, TimeUnit.SECONDS).build()



    private val deskApi by lazy {
        retrofit.create(DeskApi::class.java)
    }

//    suspend fun sendMessage(request: Request) = deskApi.sendMessage(request)
}