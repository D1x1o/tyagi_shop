package com.example.tyagi_shop.ui.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.tyagi_shop.data.RetrofitInstance
import com.example.tyagi_shop.data.model.SignUpRequest
import kotlinx.coroutines.launch

class SignUpViewModel : ViewModel() {
    val isLoading = mutableStateOf(false)
    val errorMessage = mutableStateOf<String?>(null)

    fun signUp(email: String, password: String, navController: NavController) {
        viewModelScope.launch {
            try {
                isLoading.value = true
                errorMessage.value = null

                val response = RetrofitInstance.userManagementService
                    .signUp(SignUpRequest(email, password))

                if (response.isSuccessful) {
                    navController.navigate("verifyOTP/$email/signup")
                } else {
                    when (response.code()) {
                        409 -> errorMessage.value = "Пользователь уже существует"
                        400 -> errorMessage.value = "Неверный формат email или пароля"
                        else -> errorMessage.value = "Ошибка сервера: ${response.code()}"
                    }
                }

            } catch (e: Exception) {
                errorMessage.value = "Ошибка сети: ${e.message}"
            } finally {
                isLoading.value = false
            }
        }
    }
}
