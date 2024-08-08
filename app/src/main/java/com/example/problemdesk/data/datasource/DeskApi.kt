package com.example.problemdesk.data.datasource

import com.example.problemdesk.data.models.CreateRequestRequest
import com.example.problemdesk.data.models.CreateRequestResponse
import com.example.problemdesk.data.models.LoginRequest
import com.example.problemdesk.data.models.LoginResponse
import com.example.problemdesk.data.models.MyAwardsResponse
import com.example.problemdesk.data.models.MyDataResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface DeskApi {

    @POST("/login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): LoginResponse

    @POST("/create-request")
    suspend fun createRequest(
        @Body createRequestRequest: CreateRequestRequest
    ): CreateRequestResponse

    @GET("/my-data")
    suspend fun getMyData(
        @Query("user_id") userId: Int
 //        @Body myDataRequest: MyDataRequest
    ): MyDataResponse

    @GET("rewards")
    suspend fun getMyAwards(
        @Query("user_id") userId: Int
    ): MyAwardsResponse


//
//    @POST("/create-request")
//    suspend fun createRequest(
//        @Path("request_type") requestType: Int,
//        @Path("user_id") userId: Int,
//        @Path("area_id") areaId: Int,
//        @Path("description") description: String
//    ): RequestResponse
//
//
//    @POST("/approve-request")
//    suspend fun approveRequest(
//        @Path("user_id") userId: Int,
//        @Path("request_id") requestId: Int,
//        @Path("assign_to") assignTo: Int,
//        @Path("deadline") deadline: String
//    ): RequestResponse
//
//    @POST("/reject-request")
//    suspend fun rejectRequest(
//        @Path("user_id") userId: Int,
//        @Path("request_id") requestId: Int
//    ): RequestResponse
//
//    @POST("/complete-request")
//    suspend fun completeRequest(
//        @Path("user_id") userId: Int,
//        @Path("request_id") requestId: Int
//    ): RequestResponse
//
//    @POST("/confirm-request")
//    suspend fun confirmRequest(
//        @Path("user_id") userId: Int,
//        @Path("request_id") requestId: Int
//    ): RequestResponse
//
//    @GET("/requests")
//    suspend fun getRequests(): Requests
}