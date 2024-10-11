package com.example.problemdesk

import com.example.problemdesk.data.datasource.DeskApi
import com.example.problemdesk.domain.models.Card
import com.google.gson.Gson
import kotlinx.coroutines.runBlocking
import mockwebserver3.MockResponse
import mockwebserver3.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DeskApiTest {

    private lateinit var deskApi: DeskApi
    private lateinit var mockWebServer: MockWebServer

    //перед запуском теста создаем mock-сервер
    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        val baseUrl = mockWebServer.url("/").toString()
        deskApi = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DeskApi::class.java)
    }

    //после выполнения теста выключаем mock-сервер
    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    //в этом тесте проверяется то, как приложение кидает запрос на сервер и реагирует на полученный ответ.
    // В данном случае запрашивается список заявок на выполнении (List<Card>), что является коллекцией
    @Test
    fun testGetInProgress(): Unit = runBlocking {
        // создаем ответ сервера
        val cards = listOf(
            Card(
                1,
                1,
                1,
                2,
                3,
                "туалет забился",
                1,
                "2024-01-01T00:00:00Z",
                "2024-01-02T00:00:00Z",
                "затопило весь этаж..."
            ),
            Card(
                2,
                2,
                3,
                null,
                3,
                "воняет горелой проводкой",
                1,
                "2024-01-01T00:00:00Z",
                null,
                "шел медведь по цеху, видит трансформатор горит, сел в него и сгорел"
            ),
            Card(
                3,
                4,
                2,
                null,
                4,
                "станок сломался",
                2,
                "2024-01-03T00:00:00Z",
                "2024-01-04T00:00:00Z",
                null
            )
        )

        val gson = Gson()
        val jsonResponse = gson.toJson(cards)

        // Устанавливаем серверу ответ
        mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(jsonResponse))

        // Вызываем API и получаем результат
        val result = deskApi.getInProgress(userId = 2)

        // проверяем результат
        assertEquals(cards.size, result.size)
        assertEquals(cards[0].requestId, result[0].requestId)
        assertEquals(cards[1].assignedTo, result[1].assignedTo) // Check for nullable field

        // проверяем путь эндпоинта
        val request = mockWebServer.takeRequest()
        assertEquals("/in-progress?user_id=2", request.path)
    }


    //тест, проверяющий случай когда в cards приходит не то что нужно (лишние null поля)
    @Test
    fun testInProgressNull(): Unit = runBlocking{
        // создаем ответ сервера
        val cards = listOf(
            Card(
                1,
                1,
                1,
                2,
                3,
                "туалет забился",
                1,
                "2024-01-01T00:00:00Z",
                "2024-01-02T00:00:00Z",
                "затопило весь этаж..."
            )
        )

        val fakeCard =
        " [ {\n" +
                "    \"request_id\": null,\n" +
                "    \"request_type\": 3,\n" +
                "    \"created_by\": 2,\n" +
                "    \"assigned_to\": null,\n" +
                "    \"area_id\": 1,\n" +
                "    \"description\": \"Неисправны обогреватели в раздевалке\",\n" +
                "    \"status_id\": 1,\n" +
                "    \"created_at\": \"2024-09-24T02:09:43\",\n" +
                "    \"updated_at\": null,\n" +
                "    \"reason\": null\n" +
                "  }]"

        val gson = Gson()
        val jsonResponse = gson.toJson(cards).plus(fakeCard)

        // Устанавливаем серверу ответ
        mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(jsonResponse))

        assertThrows(com.google.gson.stream.MalformedJsonException::class.java) {
            runBlocking {
                // Вызываем API и получаем результат
                deskApi.getInProgress(userId = 2)
            }
        }
    }
}