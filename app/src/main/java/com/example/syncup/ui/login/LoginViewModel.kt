package com.example.syncup.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.syncup.data.repository.auth.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel(){

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState

    fun login(email: String, password: String){
        viewModelScope.launch {
            val loginStatus = authRepository.login(email, password)
            if (loginStatus.status){
                _uiState.value = _uiState.value.copy(isLoggedIn = true)
            }
        }
    }

    fun register(username: String, email: String, password: String){
        viewModelScope.launch {
            val loginStatus = authRepository.register(username, email, password)
            if (loginStatus.status){
                _uiState.value = _uiState.value.copy(isLoggedIn = true)
            }
        }
    }

    fun logout(){
        viewModelScope.launch {
            authRepository.logout()
            _uiState.value = _uiState.value.copy(isLoggedIn = false)
        }
    }
}