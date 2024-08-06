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
        const val BASE_URL = "http://34.141.180.59:443/docs"
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

    suspend fun login(username: String, password: String, fcmToken: String) = deskApi.login(username, password, fcmToken)
    suspend fun createRequest(requestType: Int, userId: Int, areaId: Int, description: String) = deskApi.createRequest(requestType, userId, areaId, description)
    suspend fun approveRequest(userId: Int, requestId: Int, assignTo: Int, deadline: String) = deskApi.approveRequest(userId, requestId, assignTo, deadline)
    suspend fun rejectRequest(userId: Int, requestId: Int) = deskApi.rejectRequest(userId, requestId)
    suspend fun completeRequest(userId: Int, requestId: Int) = deskApi.completeRequest(userId, requestId)
    suspend fun confirmRequests(userId: Int, requestId: Int) = deskApi.confirmRequest(userId, requestId)
    suspend fun getRequests() = deskApi.getRequests()
}