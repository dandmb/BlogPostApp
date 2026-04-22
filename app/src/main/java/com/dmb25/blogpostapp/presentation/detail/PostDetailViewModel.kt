package com.dmb25.blogpostapp.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmb25.blogpostapp.domain.model.Comment
import com.dmb25.blogpostapp.domain.model.Post
import com.dmb25.blogpostapp.domain.usecase.posts.GetPostByIdUseCase
import com.dmb25.blogpostapp.domain.usecase.posts.GetPostWithCommentsUseCase
import com.dmb25.blogpostapp.domain.usecase.posts.UpdatePostUseCase
import com.dmb25.blogpostapp.presentation.events.PostEvent
import com.dmb25.blogpostapp.presentation.ui.UiState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class PostDetailViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val updatePostUseCase: UpdatePostUseCase,
    private val getPostByIdUseCase: GetPostByIdUseCase,
    private val getPostWithCommentsUseCase: GetPostWithCommentsUseCase
) : ViewModel() {
    private val postId: Int = checkNotNull(
        savedStateHandle.get<String>("postId")
    ).toInt()

    private val _uiState = MutableStateFlow<UiState<Pair<Post?, List<Comment>>>>(UiState.Idle)
    val uiState: StateFlow<UiState<Pair<Post?, List<Comment>>>> = _uiState.asStateFlow()

    private val _event = MutableSharedFlow<PostEvent>()
    val event: SharedFlow<PostEvent> = _event.asSharedFlow()

    init {
        loadPostDetails(postId)
    }

    fun loadPostDetails(postId: Int) {
        _uiState.value = UiState.Loading
        viewModelScope.launch {
            getPostWithCommentsUseCase(postId)
                .catch { e -> _uiState.value = UiState.Error(e.message ?: "Erreur") }
                .collect { postWithComments -> _uiState.value = UiState.Success(postWithComments) }
        }

    }

    fun updatePost() {
        viewModelScope.launch {
            try {
                getPostByIdUseCase(postId).collect { post ->
                    if (post != null) {
                        updatePostUseCase(post)
                            .onSuccess { _event.emit(PostEvent.PostUpdated) }
                            .onFailure { _event.emit(PostEvent.Error(it.message ?: "Erreur")) }
                    } else {
                        _event.emit(PostEvent.Error("Erreur"))
                    }
                }
            } catch (e: Exception) {
                _event.emit(PostEvent.Error(e.message ?: "Erreur"))
            }
        }
    }

}