package com.dmb25.blogpostapp.presentation.events

sealed class PostEvent {
    object PostCreated : PostEvent()
    object PostDeleted : PostEvent()

    object PostUpdated : PostEvent()
    data class Error(val message: String) : PostEvent()
}