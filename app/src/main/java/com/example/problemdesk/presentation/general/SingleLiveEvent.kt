package com.example.problemdesk.presentation.general

open class SingleLiveEvent<out T>(private val content: T) {

    private var hasBeenHandled = false

    //IDK how to handle single-time event, so ChatGPT wrote me this shit...

    // Returns the content if it has not been handled, otherwise returns null
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    // Returns the content, even if it's already been handled
    fun peekContent(): T = content
}
