package com.example.problemdesk

import com.example.problemdesk.data.models.LoginRequest
import org.junit.Assert.assertEquals
import org.junit.Test

class LoginRequestTest {

    @Test
    fun `test LoginRequest initialization`() {
        // Arrange
        val loginRequest = LoginRequest(
            username = "testUser",
            password = "securePassword123",
            fcm_token = "fcmToken123456"
        )

        // Act & Assert
        assertEquals("testUser", loginRequest.username)
        assertEquals("securePassword123", loginRequest.password)
        assertEquals("fcmToken123456", loginRequest.fcm_token)
    }

    @Test
    fun `test LoginRequest with empty values`() {
        // Arrange
        val loginRequest = LoginRequest(
            username = "",
            password = "",
            fcm_token = ""
        )

        // Act & Assert
        assertEquals("", loginRequest.username) // Testing empty string
        assertEquals("", loginRequest.password) // Testing empty string
        assertEquals("", loginRequest.fcm_token) // Testing empty string
    }
}