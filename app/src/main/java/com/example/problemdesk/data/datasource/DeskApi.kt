package com.example.problemdesk.data.datasource

import com.example.problemdesk.data.models.LoginResponse
import com.example.problemdesk.data.models.RequestResponse
import com.example.problemdesk.data.models.Requests
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface DeskApi {

    //TODO idk what the problem here
    @POST("/login/{username}{password}")
    suspend fun login(
        @Path("username") username: String,
        @Path("password") password: String
    ): LoginResponse

    @POST("/create-request")
    suspend fun createRequest(
        @Path("request_type") requestType: Int,
        @Path("user_id") userId: Int,
        @Path("area_id") areaId: Int,
        @Path("description") description: String
    ): RequestResponse


    @POST("/approve-request")
    suspend fun approveRequest(
        @Path("user_id") userId: Int,
        @Path("request_id") requestId: Int,
        @Path("assign_to") assignTo: Int,
        @Path("deadline") deadline: String
    ): RequestResponse

    @POST("/reject-request")
    suspend fun rejectRequest(
        @Path("user_id") userId: Int,
        @Path("request_id") requestId: Int
    ): RequestResponse

    @POST("/complete-request")
    suspend fun completeRequest(
        @Path("user_id") userId: Int,
        @Path("request_id") requestId: Int
    ): RequestResponse

    @POST("/confirm-request")
    suspend fun confirmRequest(
        @Path("user_id") userId: Int,
        @Path("request_id") requestId: Int
    ): RequestResponse

    @GET("/requests")
    suspend fun getRequests(): Requests
}