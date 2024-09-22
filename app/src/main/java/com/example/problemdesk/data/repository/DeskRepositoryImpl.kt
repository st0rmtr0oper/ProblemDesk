package com.example.problemdesk.data.repository

import android.content.Context
import com.example.problemdesk.R
import com.example.problemdesk.data.datasource.DeskApi
import com.example.problemdesk.data.models.CreateRequestRequest
import com.example.problemdesk.data.models.CreateRequestResponse
import com.example.problemdesk.data.models.LogOutRequest
import com.example.problemdesk.data.models.LogOutResponse
import com.example.problemdesk.data.models.LoginRequest
import com.example.problemdesk.data.models.LoginResponse
import com.example.problemdesk.data.models.MyDataResponse
import com.example.problemdesk.data.models.MyRewardsResponse
import com.example.problemdesk.data.models.RefreshRequest
import com.example.problemdesk.data.models.RefreshResponse
import com.example.problemdesk.data.models.TaskManipulationRequest
import com.example.problemdesk.data.models.TaskManipulationResponse
import com.example.problemdesk.data.sharedprefs.getSharedAuthToken
import com.example.problemdesk.domain.models.Card
import com.example.problemdesk.domain.models.RequestLog
import com.example.problemdesk.domain.repository.DeskRepository
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.KeyStore
import java.security.SecureRandom
import java.security.cert.Certificate
import java.security.cert.CertificateFactory
import java.util.concurrent.TimeUnit
import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager

class DeskRepositoryImpl(private val context: Context) : DeskRepository {
    companion object {
        const val BASE_URL = "http://timofmax1.fvds.ru:8000"
//        const val BASE_URL = "https://timofmax1.fvds.ru"
//        const val BASE_URL = "https://timofmax1.fvds.ru:443"
    }
    //TODO network test needed

    private val gson = GsonBuilder().create()

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val retrofit = Retrofit.Builder()
        .client(provideOkHttpClient())
        .baseUrl("$BASE_URL/")
        .addConverterFactory(GsonConverterFactory.create())
        //TODO it works without .create(gson)
        .build()

    //TODO AI modified (for ssl certificates)
    private fun provideOkHttpClient(): OkHttpClient {


//        // Load the self-signed certificate from raw resources
//        val certificate =
//            context.resources.openRawResource(R.raw.selfsigned) // Replace with your actual certificate name
//        val cf = CertificateFactory.getInstance("X.509")
//        val ca: Certificate = cf.generateCertificate(certificate)
//
//        // Create a KeyStore containing our trusted CAs
//        val keyStore = KeyStore.getInstance(KeyStore.getDefaultType()).apply {
//            load(null, null)
//            setCertificateEntry("ca", ca)
//        }
//
//        // Create a TrustManager that trusts the CAs in our KeyStore
//        val tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm()).apply {
//            init(keyStore)
//        }
//
//        // Create an SSLContext that uses our TrustManager
////        val sslContext = SSLContext.getInstance("TLS").apply {
////            init(null, tmf.trustManagers, null)
////        }
//
//        val sslContext = SSLContext.getInstance("TLS").apply {
//            init(null, tmf.trustManagers, SecureRandom())
//        }
//        val sslSocketFactory = sslContext.socketFactory
//        val trustManager = tmf.trustManagers[0] as X509TrustManager
//
//


        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor { chain ->
                val requestBuilder = chain.request().newBuilder()
                val token = getSharedAuthToken(context)
                if (token.isNotEmpty()) {
                    requestBuilder.addHeader("Authorization", "Bearer $token")
                }
                chain.proceed(requestBuilder.build())
            }


//            .sslSocketFactory(sslSocketFactory, trustManager) // current version
//             //            .sslSocketFactory(sslContext.socketFactory) // old version


            .connectTimeout(10L, TimeUnit.SECONDS)
            .writeTimeout(10L, TimeUnit.SECONDS)
            .readTimeout(10L, TimeUnit.SECONDS)
            .build()
    }

    private val deskApi by lazy {
        retrofit.create(DeskApi::class.java)
    }

//    init {
//        trustEveryone()
//    }

    suspend fun login(loginRequest: LoginRequest): LoginResponse = deskApi.login(loginRequest)
    suspend fun logout(logoutRequest: LogOutRequest): LogOutResponse = deskApi.logout(logoutRequest)

    suspend fun refreshUserToken(refreshRequest: RefreshRequest): RefreshResponse =
        deskApi.refreshUserToken(refreshRequest)

    suspend fun createRequest(createRequestRequest: CreateRequestRequest): CreateRequestResponse =
        deskApi.createRequest(createRequestRequest)

    suspend fun takeOnWork(request: TaskManipulationRequest): TaskManipulationResponse =
        deskApi.takeOnWork(request)

    suspend fun masterApprove(taskManipulationRequest: TaskManipulationRequest): TaskManipulationResponse =
        deskApi.masterApprove(taskManipulationRequest)

    suspend fun masterDeny(taskManipulationRequest: TaskManipulationRequest): TaskManipulationResponse =
        deskApi.masterDeny(taskManipulationRequest)

    suspend fun executorCancel(taskManipulationRequest: TaskManipulationRequest): TaskManipulationResponse =
        deskApi.executorCancel(taskManipulationRequest)

    suspend fun executorComplete(taskManipulationRequest: TaskManipulationRequest): TaskManipulationResponse =
        deskApi.executorComplete(taskManipulationRequest)

    suspend fun requestorConfirm(taskManipulationRequest: TaskManipulationRequest): TaskManipulationResponse =
        deskApi.requestorConfirm(taskManipulationRequest)

    suspend fun requestorDeny(taskManipulationRequest: TaskManipulationRequest): TaskManipulationResponse =
        deskApi.requestorDeny(taskManipulationRequest)

    suspend fun requestorDelete(taskManipulationRequest: TaskManipulationRequest): TaskManipulationResponse =
        deskApi.requestorDelete(taskManipulationRequest)

    suspend fun requestHistory(requestId: Int): List<RequestLog> = deskApi.requestHistory(requestId)

    suspend fun getMyData(userId: Int): MyDataResponse = deskApi.getMyData(userId)
    suspend fun getMyRewards(userId: Int): MyRewardsResponse = deskApi.getMyRewards(userId)

    suspend fun getExecutorUnassigned(userId: Int): List<Card> =
        deskApi.getExecutorUnassigned(userId)

    suspend fun getExecutorAssigned(userId: Int): List<Card> = deskApi.getExecutorAssigned(userId)

    suspend fun getDenied(userId: Int): List<Card> = deskApi.getDenied(userId)
    suspend fun getCompleted(userId: Int): List<Card> = deskApi.getCompleted(userId)
    suspend fun getInProgress(userId: Int): List<Card> = deskApi.getInProgress(userId)

//    suspend fun getInProgress(userId: Int): List<Card> =
//        deskApi.getUnderRequestorApproval(userId) + deskApi.getInProgress(userId)
    //TODO нужен 4 таб для этой штуки

    suspend fun getUnderMasterMonitor(userId: Int): List<Card> =
        deskApi.getUnderMasterMonitor(userId)

    suspend fun getUnderMasterApproval(userId: Int): List<Card> =
        deskApi.getUnderMasterApproval(userId)
}