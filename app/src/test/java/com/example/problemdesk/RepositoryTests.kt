package com.example.problemdesk

import android.content.Context
import com.example.problemdesk.data.datasource.DeskApi
import com.example.problemdesk.data.models.LogOutRequest
import com.example.problemdesk.data.models.LogOutResponse
import com.example.problemdesk.data.models.LoginRequest
import com.example.problemdesk.data.models.LoginResponse
import com.example.problemdesk.data.repository.DeskRepositoryImpl
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

//class DeskRepositoryImplTest {
//
//    private lateinit var context: Context
//    private lateinit var deskApi: DeskApi
//    private lateinit var deskRepository: DeskRepositoryImpl
//
//    @Before
//    fun setUp() {
//        context = mock(Context::class.java)
//        deskApi = mock(DeskApi::class.java)
//        deskRepository = DeskRepositoryImpl(context).apply {
//            // Use reflection or another method to inject mock DeskApi if needed.
//            // For this example, let's assume you can set it directly.
//            // deskApi = this@deskApi // Uncomment and modify if needed.
//        }
//    }
//
//    @Test
//    fun `test login returns expected response`() = runBlocking {
//        // Arrange
//        val loginRequest = LoginRequest("username", "password", "fcm_token")
//        val expectedResponse = LoginResponse(userId = 1, roleId = 2, accessToken = "token123")
//
//        // Mocking the API response
//        `when`(deskApi.login(loginRequest)).thenReturn(expectedResponse)
//
//        // Act
//        val actualResponse = deskRepository.login(loginRequest)
//
//        // Assert
//        assertEquals(expectedResponse, actualResponse)
//    }
//
//    @Test(expected = Exception::class)
//    fun `test login throws exception on failure`(): Unit = runBlocking {
//        // Arrange
//        val loginRequest = LoginRequest("username", "password", "fcm_token")
//
//        // Mocking the API to throw an exception
//        `when`(deskApi.login(loginRequest)).thenThrow(Exception("Login failed"))
//
//        // Act
//        deskRepository.login(loginRequest) // This should throw an exception
//
//        // Assert is handled by the expected annotation
//    }
//
//    @Test
//    fun `test logout returns expected response`() = runBlocking {
//        // Arrange
//        val logoutRequest = LogOutRequest(user_id = 1, old_fcm = "sfmslrflsenfnsdlfgrnlsdnfglsndflgnsdlfnglsdfnglsd")
//        val expectedResponse = LogOutResponse(message = "success")
//
//        // Mocking the API response
//        `when`(deskApi.logout(logoutRequest)).thenReturn(expectedResponse)
//
//        // Act
//        val actualResponse = deskRepository.logout(logoutRequest)
//
//        // Assert
//        assertEquals(expectedResponse, actualResponse)
//    }
//
//    // Additional tests for other methods can be added similarly...
//}