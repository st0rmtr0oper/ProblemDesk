import com.example.problemdesk.domain.models.Card
import com.example.problemdesk.domain.models.RequestLog
import com.example.problemdesk.domain.models.Specialization
import com.example.problemdesk.domain.models.UserRating
import org.junit.Assert.assertEquals
import org.junit.Test

class CardTest {

    @Test
    fun `test Card initialization`() {
        // Arrange
        val card = Card(
            requestId = 1,
            requestType = 2,
            createdBy = 3,
            assignedTo = 4,
            areaId = 1,
            description = "Описание",
            statusId = 6,
            createdAt = "2024-09-26T12:00:00Z",
            updatedAt = "2024-09-26T12:00:00Z",
            reason = "Пометка"
        )

        // Act & Assert
        assertEquals(1, card.requestId)
        assertEquals(2, card.requestType)
        assertEquals(3, card.createdBy)
        assertEquals(4, card.assignedTo)
        assertEquals(1, card.areaId)
        assertEquals("Описание", card.description)
        assertEquals(6, card.statusId)
        assertEquals("2024-09-26T12:00:00Z", card.createdAt)
        assertEquals("2024-09-26T12:00:00Z", card.updatedAt)
        assertEquals("Пометка", card.reason)
    }
}

class RequestLogTest {

    @Test
    fun `test RequestLog initialization`() {
        // Arrange
        val requestLog = RequestLog(
            logId = 1,
            requestId = 100,
            oldStatusId = 2,
            newStatusId = 3,
            changedAt = "2024-09-26T12:00:00Z",
            changedBy = 5,
            reason = "Status updated",
            changerName = "John Doe",
            actionName = "Update"
        )

        // Act & Assert
        assertEquals(1, requestLog.logId)
        assertEquals(100, requestLog.requestId)
        assertEquals(2, requestLog.oldStatusId)
        assertEquals(3, requestLog.newStatusId)
        assertEquals("2024-09-26T12:00:00Z", requestLog.changedAt)
        assertEquals(5, requestLog.changedBy)
        assertEquals("Status updated", requestLog.reason)
        assertEquals("John Doe", requestLog.changerName)
        assertEquals("Update", requestLog.actionName)
    }

    @Test
    fun `test RequestLog with null values`() {
        // Arrange
        val requestLog = RequestLog(
            logId = 2,
            requestId = 101,
            oldStatusId = null,
            newStatusId = 4,
            changedAt = "2024-09-26T12:30:00Z",
            changedBy = 6,
            reason = null,
            changerName = null,
            actionName = "Create"
        )

        // Act & Assert
        assertEquals(2, requestLog.logId)
        assertEquals(101, requestLog.requestId)
        assertEquals(null, requestLog.oldStatusId) // Testing nullable field
        assertEquals(4, requestLog.newStatusId)
        assertEquals("2024-09-26T12:30:00Z", requestLog.changedAt)
        assertEquals(6, requestLog.changedBy)
        assertEquals(null, requestLog.reason) // Testing nullable field
        assertEquals(null, requestLog.changerName) // Testing nullable field
        assertEquals("Create", requestLog.actionName)
    }
}

class SpecializationTest {

    @Test
    fun `test Specialization initialization`() {
        // Arrange
        val specialization = Specialization(
            name = "Разработчик",
            id = 101
        )

        // Act & Assert
        assertEquals("Разработчик", specialization.name)
        assertEquals(101, specialization.id)
    }

    @Test
    fun `test Specialization toString`() {
        // Arrange
        val specialization = Specialization(
            name = "Электрик",
            id = 102
        )

        // Act
        val result = specialization.toString()

        // Assert
        assertEquals("Электрик", result)
    }
}

class UserRatingTest {

    @Test
    fun `test UserRating initialization`() {
        // Arrange
        val userRating = UserRating(
            userId = "12345",
            surname = "Doe",
            name = "John",
            middleName = "A.",
            specialization = "Разработчик",
            tokens = 10,
            numCreated = 5,
            numCompleted = 3
        )

        // Act & Assert
        assertEquals("12345", userRating.userId)
        assertEquals("Doe", userRating.surname)
        assertEquals("John", userRating.name)
        assertEquals("A.", userRating.middleName)
        assertEquals("Разработчик", userRating.specialization)
        assertEquals(10, userRating.tokens)
        assertEquals(5, userRating.numCreated)
        assertEquals(3, userRating.numCompleted)
    }

    @Test
    fun `test UserRating with null values`() {
        // Arrange
        val userRating = UserRating(
            userId = "67890",
            surname = null,
            name = null,
            middleName = null,
            specialization = "Электрик",
            tokens = 15,
            numCreated = 2,
            numCompleted = 1
        )

        // Act & Assert
        assertEquals("67890", userRating.userId)
        assertEquals(null, userRating.surname) // Testing nullable field
        assertEquals(null, userRating.name) // Testing nullable field
        assertEquals(null, userRating.middleName) // Testing nullable field
        assertEquals("Электрик", userRating.specialization)
        assertEquals(15, userRating.tokens)
        assertEquals(2, userRating.numCreated)
        assertEquals(1, userRating.numCompleted)
    }
}