package com.example.problemdesk.data.localstorage

class LocalStorage {

    private var value: String = ""

    fun putInto(newValue: String) {
        value = newValue
    }

    fun getString(): String {
        return value
    }
}

//TODO this is there only for a task. DELETE later