package com.example.problemdesk.data.repository

import android.content.Context
import androidx.media3.common.util.Log
import com.example.problemdesk.data.datasource.DeskApi
//import com.example.problemdesk.data.models.AuthTokenRequest
//import com.example.problemdesk.data.models.AuthTokenResponse
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
import com.example.problemdesk.data.sharedprefs.PreferenceUtil
import com.example.problemdesk.data.sharedprefs.getSharedAuthToken
import com.example.problemdesk.domain.models.Card
import com.example.problemdesk.domain.models.RequestLog
import com.example.problemdesk.domain.repository.DeskRepository
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class DeskRepositoryImpl(private val context: Context) : DeskRepository {
    companion object {
        const val BASE_URL = "http://timofmax1.fvds.ru:8000"
    }

    private val gson = GsonBuilder().create()

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }




    //TODO is it safe?
//    private var bearerToken: String? = null


    private val retrofit = Retrofit.Builder()
        .client(provideOkHttpClient(/*bearerToken*/))
        .baseUrl("$BASE_URL/")
        .addConverterFactory(GsonConverterFactory.create())
        //TODO it works without .create(gson)
        .build()



//    private fun provideOkHttpClient(): OkHttpClient {
//        return OkHttpClient.Builder() // Use OkHttpClient.Builder directly
//            .addInterceptor(loggingInterceptor)
//
//
//
//            .addInterceptor{chain ->
//                val original = chain.request()
//                val request = original.newBuilder()
//                    .header("Authorization", "Bearer $bearerToken")
//                    .method(original.method, original.body)
//                    .build()
//                chain.proceed(request)
//            }// Add the logging interceptor
//
//            .connectTimeout(10L, TimeUnit.SECONDS) // Set connection timeout
//            .writeTimeout(10L, TimeUnit.SECONDS) // Set write timeout
//            .readTimeout(10L, TimeUnit.SECONDS) // Set read timeout
//            .build() // Build the OkHttpClient instance
//    }




    private fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()


            .addInterceptor(loggingInterceptor) // Add logging interceptor

//            .addInterceptor(authTokenInterceptor)

            .addInterceptor { chain ->
                val requestBuilder = chain.request().newBuilder()
                // Get the token from the token provider and add it to the Authorization header
                //TODO idk
                val token = getSharedAuthToken(context)
//                val token = bearerToken/*tokenProvider()*/
                //TODO !!
                if (token.isNotEmpty()) {
                    requestBuilder.addHeader("Authorization", "Bearer $token")
                }
                chain.proceed(requestBuilder.build())
            }

            .connectTimeout(10L, TimeUnit.SECONDS)
            .writeTimeout(10L, TimeUnit.SECONDS)
            .readTimeout(10L, TimeUnit.SECONDS)
            .build()
    }

    private val deskApi by lazy {
        retrofit.create(DeskApi::class.java)
    }

    suspend fun login(loginRequest: LoginRequest): LoginResponse {
        val response = deskApi.login(loginRequest)
//        bearerToken = response.accessToken
//        Log.d("AuthToken", "Bearer Token: $bearerToken") // Log the token
        return response
    }

    suspend fun logout(logoutRequest: LogOutRequest): LogOutResponse {
        val response = deskApi.logout(logoutRequest)
//        bearerToken = null
        return response
    }
    //TODO i dont need it
//    suspend fun authToken(authTokenRequest: AuthTokenRequest): AuthTokenResponse = deskApi.authToken(authTokenRequest)
    //TODO
    suspend fun refreshUserToken(refreshRequest: RefreshRequest): RefreshResponse = deskApi.refreshUserToken(refreshRequest)

    suspend fun createRequest(createRequestRequest: CreateRequestRequest): CreateRequestResponse = deskApi.createRequest(createRequestRequest)

    suspend fun takeOnWork(request: TaskManipulationRequest): TaskManipulationResponse = deskApi.takeOnWork(request)
    suspend fun masterApprove(taskManipulationRequest: TaskManipulationRequest): TaskManipulationResponse = deskApi.masterApprove(taskManipulationRequest)
    suspend fun masterDeny(taskManipulationRequest: TaskManipulationRequest): TaskManipulationResponse = deskApi.masterDeny(taskManipulationRequest)
    suspend fun executorCancel(taskManipulationRequest: TaskManipulationRequest): TaskManipulationResponse = deskApi.executorCancel(taskManipulationRequest)
    suspend fun executorComplete(taskManipulationRequest: TaskManipulationRequest): TaskManipulationResponse = deskApi.executorComplete(taskManipulationRequest)
    suspend fun requestorConfirm(taskManipulationRequest: TaskManipulationRequest): TaskManipulationResponse = deskApi.requestorConfirm(taskManipulationRequest)
    suspend fun requestorDeny(taskManipulationRequest: TaskManipulationRequest): TaskManipulationResponse = deskApi.requestorDeny(taskManipulationRequest)
    suspend fun requestorDelete(taskManipulationRequest: TaskManipulationRequest): TaskManipulationResponse = deskApi.requestorDelete(taskManipulationRequest)

    suspend fun requestHistory(requestId: Int): List<RequestLog> = deskApi.requestHistory(requestId)

    suspend fun getMyData(userId: Int): MyDataResponse = deskApi.getMyData(userId)
    suspend fun getMyRewards(userId: Int): MyRewardsResponse = deskApi.getMyRewards(userId)

    suspend fun getExecutorUnassigned(userId: Int): List<Card> = deskApi.getExecutorUnassigned(userId)
    suspend fun getExecutorAssigned(userId: Int): List<Card> = deskApi.getExecutorAssigned(userId)

    suspend fun getDenied(userId: Int): List<Card> = deskApi.getDenied(userId)
    suspend fun getCompleted(userId: Int): List<Card> = deskApi.getCompleted(userId)
    suspend fun getInProgress(userId: Int): List<Card> = deskApi.getUnderRequestorApproval(userId) + deskApi.getInProgress(userId)

    suspend fun getUnderMasterMonitor(userId: Int): List<Card> = deskApi.getUnderMasterMonitor(userId)
    suspend fun getUnderMasterApproval(userId: Int): List<Card> = deskApi.getUnderMasterApproval(userId)
}