package com.dmb25.blogpostapp.presentation.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmb25.blogpostapp.domain.model.User
import com.dmb25.blogpostapp.domain.usecase.users.GetUserByIdUseCase
import com.dmb25.blogpostapp.domain.usecase.users.GetUsersUseCase
import com.dmb25.blogpostapp.presentation.ui.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class UsersViewModel(
    private val getUsersUseCase: GetUsersUseCase
) : ViewModel(){
    private val _uiState = MutableStateFlow<UiState<List<User>>>(UiState.Idle)
    val uiState: StateFlow<UiState<List<User>>> = _uiState.asStateFlow()

    init {
        loadUsers()
    }

    fun loadUsers() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            getUsersUseCase()
                .catch { e -> _uiState.value = UiState.Error(e.message ?: "Erreur") }
                .collect { users -> _uiState.value = UiState.Success(users) }
        }
    }
}