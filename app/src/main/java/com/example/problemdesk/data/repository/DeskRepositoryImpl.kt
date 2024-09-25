package com.example.problemdesk.data.repository

import android.content.Context
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
import com.example.problemdesk.domain.models.MockStat
import com.example.problemdesk.domain.models.RequestLog
import com.example.problemdesk.domain.models.UserRating
import com.example.problemdesk.domain.repository.DeskRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSession
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager


class DeskRepositoryImpl(private val context: Context) : DeskRepository {

    companion object {
        const val BASE_URL = "https://timofmax1.fvds.ru:443"
    }

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val retrofit = Retrofit.Builder()
        .client(provideOkHttpClient())
        .baseUrl("$BASE_URL/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    private fun provideOkHttpClient(): OkHttpClient {

        val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
            @Throws(CertificateException::class)
            override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {
            }

            @Throws(CertificateException::class)
            override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {
            }

            override fun getAcceptedIssuers(): Array<X509Certificate> {
                return arrayOf()
            }
        }
        )

        val sslContext = SSLContext.getInstance("SSL")
        sslContext.init(null, trustAllCerts, SecureRandom())
        val sslSocketFactory: SSLSocketFactory = sslContext.socketFactory

        val builder: OkHttpClient.Builder = OkHttpClient.Builder()
        builder
            .addInterceptor(loggingInterceptor)
            .addInterceptor { chain ->
                val requestBuilder = chain.request().newBuilder()
//                requestBuilder.addHeader("accept: ","application/json")
//                requestBuilder.addHeader("Content-Type: ","application/json")
                val token = getSharedAuthToken(context)
                if (token.isNotEmpty()) {
                    requestBuilder.addHeader("Authorization", "Bearer $token")
                }
                chain.proceed(requestBuilder.build())
            }
            .sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
            .hostnameVerifier(object: HostnameVerifier {
                override fun verify(hostname: String?, session: SSLSession?): Boolean {
                    return true
                }
            })
            .connectTimeout(10L, TimeUnit.SECONDS)
            .writeTimeout(10L, TimeUnit.SECONDS)
            .readTimeout(10L, TimeUnit.SECONDS)

        val okHttpClient: OkHttpClient = builder.build()
        return okHttpClient
    }

    private val deskApi by lazy {
        retrofit.create(DeskApi::class.java)
    }

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

    suspend fun bossRequests(fromDate: String, untilDate: String, status: String, requestType: Int, areaId: Int): List<Card> =
        deskApi.bossRequests(fromDate, untilDate, status, requestType, areaId)

    suspend fun getAllStats(): List<MockStat> = deskApi.getAllStats()

    suspend fun getRating(): List<UserRating> = deskApi.getRating()
}