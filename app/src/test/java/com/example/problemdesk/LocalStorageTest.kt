package com.example.problemdesk

import org.junit.Before
import com.example.problemdesk.data.localstorage.LocalStorage
import org.junit.Test
import org.junit.Assert.assertEquals


//тест на проверку работы локального хранилища
class LocalStorageTest {

    private lateinit var localStorage: LocalStorage

    @Before
    fun setUp() {
        localStorage = LocalStorage()
    }

    @Test
    fun localStoragePitIntoTest() {
        val newValue = "new value"

        //кладем новое значение в локальное хранилище. метод ничего не возвращает
        localStorage.putInto(newValue)

        //достаем оттуда значение
        val result = localStorage.getString()

        //сравниваем
        assertEquals(newValue, result)
    }
}