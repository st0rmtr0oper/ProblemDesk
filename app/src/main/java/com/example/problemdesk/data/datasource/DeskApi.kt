package com.example.problemdesk.data.datasource

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
import com.example.problemdesk.domain.models.Card
import com.example.problemdesk.domain.models.RequestLog
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

    @POST("/refresh-user-token")
    suspend fun refreshUserToken(
        @Body refreshRequest: RefreshRequest
    ): RefreshResponse

//    @POST("/token")
//    suspend fun authToken(
//        @Body authTokenRequest: AuthTokenRequest
//    ): AuthTokenResponse



    @POST("/create-request")
    suspend fun createRequest(
        @Body createRequestRequest: CreateRequestRequest
    ): CreateRequestResponse

    @POST("/take-on-work")
    suspend fun takeOnWork(
        @Body taskManipulationRequest: TaskManipulationRequest
    ): TaskManipulationResponse

    @POST("/master-approve")
    suspend fun masterApprove(
        @Body taskManipulationRequest: TaskManipulationRequest
    ): TaskManipulationResponse

    @POST("/master-deny")
    suspend fun masterDeny(
        @Body taskManipulationRequest: TaskManipulationRequest
    ): TaskManipulationResponse

    @POST("/executor-cancel")
    suspend fun executorCancel(
        @Body taskManipulationRequest: TaskManipulationRequest
    ): TaskManipulationResponse

    @POST("/executor-complete")
    suspend fun executorComplete(
        @Body taskManipulationRequest: TaskManipulationRequest
    ): TaskManipulationResponse

    @POST("/requestor-confirm")
    suspend fun requestorConfirm(
        @Body taskManipulationRequest: TaskManipulationRequest
    ): TaskManipulationResponse

    @POST("/requestor-deny")
    suspend fun requestorDeny(
        @Body taskManipulationRequest: TaskManipulationRequest
    ): TaskManipulationResponse

    @POST("/requestor-delete")
    suspend fun requestorDelete(
        @Body taskManipulationRequest: TaskManipulationRequest
    ): TaskManipulationResponse



    @GET("/request-history")
    suspend fun requestHistory(
        @Query("request_id") userId: Int
    ): List<RequestLog>



    @GET("/my-data")
    suspend fun getMyData(
        @Query("user_id") userId: Int
    ): MyDataResponse

    @GET("/rewards")
    suspend fun getMyRewards(
        @Query("user_id") userId: Int
    ): MyRewardsResponse

    @GET("/executor-unassigned")
    suspend fun getExecutorUnassigned(
        @Query("user_id") userId: Int
    ): List<Card>

    @GET("/executor-assigned")
    suspend fun getExecutorAssigned(
        @Query("user_id") userId: Int
    ): List<Card>

    @GET("/denied")
    suspend fun getDenied(
        @Query("user_id") userId: Int
    ): List<Card>

    @GET("/completed")
    suspend fun getCompleted(
        @Query("user_id") userId: Int
    ): List<Card>

    @GET("/under-master-approval")
    suspend fun getUnderMasterApproval(
        @Query("user_id") userId: Int
    ): List<Card>

    @GET("/under-master-monitor")
    suspend fun getUnderMasterMonitor(
        @Query("user_id") userId: Int
    ): List<Card>

    @GET("/in-progress")
    suspend fun getInProgress(
        @Query("user_id") userId: Int
    ): List<Card>

    @GET("/under-requestor-approval")
    suspend fun getUnderRequestorApproval(
        @Query("user_id") userId: Int
    ): List<Card>
}