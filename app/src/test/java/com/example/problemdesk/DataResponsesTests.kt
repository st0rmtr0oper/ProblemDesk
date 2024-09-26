package com.example.problemdesk

import com.example.problemdesk.data.models.LoginResponse
import com.example.problemdesk.data.models.MyDataResponse
import org.junit.Assert.assertEquals
import org.junit.Test

class LoginResponseTest {

    @Test
    fun `test LoginResponse initialization`() {
        // Arrange
        val loginResponse = LoginResponse(
            userId = 123,
            roleId = 1,
            accessToken = "sampleAccessToken"
        )

        // Act & Assert
        assertEquals(123, loginResponse.userId)
        assertEquals(1, loginResponse.roleId)
        assertEquals("sampleAccessToken", loginResponse.accessToken)
    }

    @Test
    fun `test LoginResponse with empty access token`() {
        // Arrange
        val loginResponse = LoginResponse(
            userId = 456,
            roleId = 2,
            accessToken = ""
        )

        // Act & Assert
        assertEquals(456, loginResponse.userId)
        assertEquals(2, loginResponse.roleId)
        assertEquals("", loginResponse.accessToken) // Testing with empty string
    }
}

class MyDataResponseTest {

    @Test
    fun `test MyDataResponse initialization`() {
        // Arrange
        val myDataResponse = MyDataResponse(
            userId = 1,
            username = "johndoe",
            surname = "Doe",
            name = "John",
            middleName = "A.",
            hireDate = "2024-01-01",
            phoneNumber = "+1234567890",
            birthDate = "1990-01-01",
            email = "johndoe@example.com",
            specId = 101,
            fcmToken = listOf("token1", "token2"),
            roleId = 2,
            shiftId = 3
        )

        // Act & Assert
        assertEquals(1, myDataResponse.userId)
        assertEquals("johndoe", myDataResponse.username)
        assertEquals("Doe", myDataResponse.surname)
        assertEquals("John", myDataResponse.name)
        assertEquals("A.", myDataResponse.middleName)
        assertEquals("2024-01-01", myDataResponse.hireDate)
        assertEquals("+1234567890", myDataResponse.phoneNumber)
        assertEquals("1990-01-01", myDataResponse.birthDate)
        assertEquals("johndoe@example.com", myDataResponse.email)
        assertEquals(101, myDataResponse.specId)
        assertEquals(listOf("token1", "token2"), myDataResponse.fcmToken)
        assertEquals(2, myDataResponse.roleId)
        assertEquals(3, myDataResponse.shiftId)
    }

    @Test
    fun `test MyDataResponse with empty fields`() {
        // Arrange
        val myDataResponse = MyDataResponse(
            userId = 2,
            username = "",
            surname = "",
            name = "",
            middleName = "",
            hireDate = "",
            phoneNumber = "",
            birthDate = "",
            email = "",
            specId = 0,
            fcmToken = emptyList(),
            roleId = 0,
            shiftId = 0
        )

        // Act & Assert
        assertEquals(2, myDataResponse.userId)
        assertEquals("", myDataResponse.username) // Testing with empty string
        assertEquals("", myDataResponse.surname) // Testing with empty string
        assertEquals("", myDataResponse.name) // Testing with empty string
        assertEquals("", myDataResponse.middleName) // Testing with empty string
        assertEquals("", myDataResponse.hireDate) // Testing with empty string
        assertEquals("", myDataResponse.phoneNumber) // Testing with empty string
        assertEquals("", myDataResponse.birthDate) // Testing with empty string
        assertEquals("", myDataResponse.email) // Testing with empty string
        assertEquals(0, myDataResponse.specId)
        assertEquals(emptyList<String>(), myDataResponse.fcmToken) // Testing with empty list
        assertEquals(0, myDataResponse.roleId)
        assertEquals(0, myDataResponse.shiftId)
    }
}