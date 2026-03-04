package com.example.tyagi_shop.ui.viewModel

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tyagi_shop.data.RetrofitInstance
import com.example.tyagi_shop.data.model.FavouriteRequest
import kotlinx.coroutines.launch

class HomeViewModel: ViewModel() {
    val errorMessage = mutableStateOf("")
    val favouriteState = mutableStateOf(false)
    fun addToFavourite(
        productId: String,
        userId: String?
    ) {
        viewModelScope.launch {
            try {
                val request = FavouriteRequest(
                    product_id = productId,
                    user_id = userId
                )

                val response =
                    RetrofitInstance.userManagementService
                        .addToFavourite(favouriteRequest = request)

                if (response.isSuccessful) {
                    favouriteState.value = true
                    errorMessage.value = response.code().toString()
                } else {

                    when (response.code()) {

                        401 -> errorMessage.value = "Не авторизован"
                        403 -> errorMessage.value = "Нет доступа"
                        409 -> errorMessage.value = "Уже в избранном"
                        else -> errorMessage.value =
                            "Ошибка сервера: ${response.code()}"
                    }
                    Log.e("Add To Favourite",errorMessage.value)
                }

            } catch (e: Exception) {
                favouriteState.value = false
                errorMessage.value = "Ошибка: ${e.message}"

            }
        }
    }
}
