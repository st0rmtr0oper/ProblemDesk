package com.example.problemdesk.data.datasource

import com.example.problemdesk.data.models.CreateRequestRequest
import com.example.problemdesk.data.models.CreateRequestResponse
import com.example.problemdesk.data.models.LogOutRequest
import com.example.problemdesk.data.models.LogOutResponse
import com.example.problemdesk.data.models.LoginRequest
import com.example.problemdesk.data.models.LoginResponse
import com.example.problemdesk.data.models.MyAwardsResponse
import com.example.problemdesk.data.models.MyDataResponse
import com.example.problemdesk.domain.models.Card
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface DeskApi {

    @POST("/login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): LoginResponse

    @POST("/logout")
    suspend fun logout(
        @Body logoutRequest: LogOutRequest
    ): LogOutResponse

    @POST("/create-request")
    suspend fun createRequest(
        @Body createRequestRequest: CreateRequestRequest
    ): CreateRequestResponse

    @GET("/my-data")
    suspend fun getMyData(
        @Query("user_id") userId: Int
    ): MyDataResponse

    @GET("rewards")
    suspend fun getMyAwards(
        @Query("user_id") userId: Int
    ): MyAwardsResponse

    @GET("executor-unassigned")
    suspend fun getExecutorUnassigned(
        @Query("user_id") userId: Int
    ): List<Card>

    @GET("executor-assigned")
    suspend fun getExecutorAssigned(
        @Query("user_id") userId: Int
    ): List<Card>

    @GET("denied")
    suspend fun getDenied(
        @Query("user_id") userId: Int
    ): List<Card>

    @GET("completed")
    suspend fun getCompleted(
        @Query("user_id") userId: Int
    ): List<Card>

    @GET("under-master-approval")
    suspend fun getUnderMasterApproval(
        @Query("user_id") userId: Int
    ): List<Card>

    @GET("under-master-monitor")
    suspend fun getUnderMasterMonitor(
        @Query("user_id") userId: Int
    ): List<Card>

    @GET("in-progress")
    suspend fun getInProgress(
        @Query("user_id") userId: Int
    ): List<Card>

    @GET("under-requestor-approval")
    suspend fun getUnderRequestorApproval(
        @Query("user_id") userId: Int
    ): List<Card>


//    @GET("executor-assigned")
//    suspend fun getExecutorAssigned(
//        @Query("user_id") userId: Int
//    ): List<Card>
}