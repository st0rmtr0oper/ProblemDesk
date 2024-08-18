package com.example.problemdesk.data.repository

import com.example.problemdesk.data.datasource.DeskApi
import com.example.problemdesk.data.models.CreateRequestRequest
import com.example.problemdesk.data.models.CreateRequestResponse
import com.example.problemdesk.data.models.LogOutRequest
import com.example.problemdesk.data.models.LogOutResponse
import com.example.problemdesk.data.models.LoginRequest
import com.example.problemdesk.data.models.LoginResponse
import com.example.problemdesk.data.models.MyRewardsResponse
import com.example.problemdesk.data.models.MyDataResponse
import com.example.problemdesk.data.models.RefreshRequest
import com.example.problemdesk.data.models.RefreshResponse
import com.example.problemdesk.data.models.TaskManipulationRequest
import com.example.problemdesk.data.models.TaskManipulationResponse
import com.example.problemdesk.domain.models.Card
import com.example.problemdesk.domain.models.RequestLog
import com.example.problemdesk.domain.repository.DeskRepository
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class DeskRepositoryImplementation : DeskRepository {
    companion object {
        const val BASE_URL = "http://34.141.180.59:443"
    }

    private val gson = GsonBuilder().create()

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val retrofit = Retrofit.Builder()
        .client(provideOkHttpClient())
        .baseUrl("$BASE_URL/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    private fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder() // Use OkHttpClient.Builder directly
            .addInterceptor(loggingInterceptor) // Add the logging interceptor
            .connectTimeout(10L, TimeUnit.SECONDS) // Set connection timeout
            .writeTimeout(10L, TimeUnit.SECONDS) // Set write timeout
            .readTimeout(10L, TimeUnit.SECONDS) // Set read timeout
            .build() // Build the OkHttpClient instance
    }

    private val deskApi by lazy {
        retrofit.create(DeskApi::class.java)
    }

    suspend fun login(loginRequest: LoginRequest): LoginResponse = deskApi.login(loginRequest)
    suspend fun logout(logoutRequest: LogOutRequest): LogOutResponse = deskApi.logout(logoutRequest)
    //TODO
    suspend fun refreshUserToken(refreshRequest: RefreshRequest): RefreshResponse = deskApi.refreshUserToken(refreshRequest)

    suspend fun createRequest(createRequestRequest: CreateRequestRequest): CreateRequestResponse = deskApi.createRequest(createRequestRequest)
    suspend fun takeOnWork(request: TaskManipulationRequest): TaskManipulationResponse = deskApi.takeOnWork(request)
    //TODO
    suspend fun masterApprove(taskManipulationRequest: TaskManipulationRequest): TaskManipulationResponse = deskApi.masterApprove(taskManipulationRequest)
    suspend fun masterDeny(taskManipulationRequest: TaskManipulationRequest): TaskManipulationResponse = deskApi.masterDeny(taskManipulationRequest)
    suspend fun executorCancel(taskManipulationRequest: TaskManipulationRequest): TaskManipulationResponse = deskApi.executorCancel(taskManipulationRequest)
    suspend fun executorComplete(taskManipulationRequest: TaskManipulationRequest): TaskManipulationResponse = deskApi.executorComplete(taskManipulationRequest)
    suspend fun requestorConfirm(taskManipulationRequest: TaskManipulationRequest): TaskManipulationResponse = deskApi.requestorConfirm(taskManipulationRequest)
    suspend fun requestorDeny(taskManipulationRequest: TaskManipulationRequest): TaskManipulationResponse = deskApi.requestorDeny(taskManipulationRequest)
    suspend fun requestorDelete(taskManipulationRequest: TaskManipulationRequest): TaskManipulationResponse = deskApi.requestorDelete(taskManipulationRequest)

    //TODO
    suspend fun requestHistory(requestId: Int): List<RequestLog> = deskApi.requestHistory(requestId)

    suspend fun getMyData(userId: Int): MyDataResponse = deskApi.getMyData(userId)
    suspend fun getMyRewards(userId: Int): MyRewardsResponse = deskApi.getMyRewards(userId)

    suspend fun getExecutorUnassigned(userId: Int): List<Card> = deskApi.getExecutorUnassigned(userId)
    suspend fun getExecutorAssigned(userId: Int): List<Card> = deskApi.getExecutorAssigned(userId)

    suspend fun getDenied(userId: Int): List<Card> = deskApi.getDenied(userId)
    suspend fun getCompleted(userId: Int): List<Card> = deskApi.getCompleted(userId)
    suspend fun getInProgress(userId: Int): List<Card> = deskApi.getInProgress(userId) + deskApi.getUnderRequestorApproval(userId)

    //TODO
    suspend fun getUnderMasterMonitor(userId: Int): List<Card> = deskApi.getUnderMasterMonitor(userId)
    suspend fun getUnderMasterApproval(userId: Int): List<Card> = deskApi.getUnderMasterApproval(userId)
}