package com.dmb25.blogpostapp.presentation.posts

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmb25.blogpostapp.domain.model.Post
import com.dmb25.blogpostapp.domain.model.User
import com.dmb25.blogpostapp.domain.usecase.posts.CreatePostUseCase
import com.dmb25.blogpostapp.domain.usecase.posts.DeletePostUseCase
import com.dmb25.blogpostapp.domain.usecase.posts.GetUserWithPostsUseCase
import com.dmb25.blogpostapp.presentation.events.PostEvent
import com.dmb25.blogpostapp.presentation.ui.UiState
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
class PostsViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val createPostUseCase: CreatePostUseCase,
    private val deletePostUseCase: DeletePostUseCase,
    private val getUserWithPostsUseCase: GetUserWithPostsUseCase
) : ViewModel() {

    val userId: Int = checkNotNull(
        savedStateHandle.get<Int>("userId")
    )

    private val _uiState = MutableStateFlow<UiState<Pair<User?, List<Post>>>>(UiState.Idle)
    val uiState: StateFlow<UiState<Pair<User?, List<Post>>>> = _uiState.asStateFlow()
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _events = MutableSharedFlow<PostEvent>()
    val events: SharedFlow<PostEvent> = _events.asSharedFlow()

    init {
        loadUserWithPosts(userId)
    }

    private val _filteredPosts = combine(
        _uiState,
        _searchQuery
            .debounce(300)
            .distinctUntilChanged()
    ) { state, query ->
        when (state) {
            is UiState.Success -> {
                val filtered = state.data.second.filter {
                    it.title.contains(query, ignoreCase = true)
                }
                UiState.Success(Pair(state.data.first, filtered))
            }

            else -> state
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = UiState.Idle
    )
    val filteredPosts: StateFlow<UiState<Pair<User?, List<Post>>>> = _filteredPosts


    fun loadUserWithPosts(userId: Int) {
        _uiState.value = UiState.Loading
        viewModelScope.launch {
            getUserWithPostsUseCase(userId)
                .catch { e -> _uiState.value = UiState.Error(e.message ?: "Erreur") }
                .collect { userWithPosts -> _uiState.value = UiState.Success(userWithPosts) }
        }
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    fun createPost(post: Post) {
        viewModelScope.launch {
            createPostUseCase(post)
                .onSuccess { _events.emit(PostEvent.PostCreated) }
                .onFailure { _events.emit(PostEvent.Error(it.message ?: "Erreur")) }
        }
    }

    fun deletePost(id: Int) {
        viewModelScope.launch {
            deletePostUseCase(id)
                .onSuccess { _events.emit(PostEvent.PostDeleted) }
                .onFailure { _events.emit(PostEvent.Error(it.message ?: "Erreur")) }
        }
    }

}