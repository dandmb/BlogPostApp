package com.dmb25.blogpostapp.presentation.state

sealed class UiState{
    object Loading : UiState()
//    data class Success(val data: List<Post>) : UiState()
}
