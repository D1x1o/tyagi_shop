package com.example.tyagi_shop.data.repository

import Product
import android.util.Log
import com.example.tyagi_shop.data.RetrofitInstance
import com.example.tyagi_shop.data.service.UserManagementService
import retrofit2.Response


class ProductRepository {


    private val tag = "ProductRepository"

    // Получить все продукты
    suspend fun getProducts(token: String): List<Product>? {
        return try {
            Log.d(tag, "Getting all products")
            val response = RetrofitInstance.userManagementService.getProducts2("Bearer $token")

            if (response.isSuccessful) {
                val products = response.body()
                Log.d(tag, "Products loaded: ${products?.size}")
                products
            } else {
                val errorBody = response.errorBody()?.string()
                Log.e(tag, "Error response: $errorBody")
                null
            }
        } catch (e: Exception) {
            Log.e(tag, "Exception in getProducts", e)
            null
        }
    }

    // Получить продукт по ID
    suspend fun getProductById(productId: String, token: String): Product? {
        return try {
            Log.d(tag, "Getting product: $productId")
            val filter = "eq.$productId"
            val response = RetrofitInstance.userManagementService.getProductById(filter, "Bearer $token")

            if (response.isSuccessful) {
                val products = response.body()
                products?.firstOrNull()
            } else {
                val errorBody = response.errorBody()?.string()
                Log.e(tag, "Error response: $errorBody")
                null
            }
        } catch (e: Exception) {
            Log.e(tag, "Exception in getProductById", e)
            null
        }
    }

    // Получить продукты по категории
    suspend fun getProductsByCategory(categoryId: String, token: String): List<Product>? {
        return try {
            Log.d(tag, "Getting products for category: $categoryId")
            val filter = "eq.$categoryId"
            val response = RetrofitInstance.userManagementService.getProductsByCategory(filter, "Bearer $token")

            if (response.isSuccessful) {
                val products = response.body()
                Log.d(tag, "Products loaded: ${products?.size}")
                products
            } else {
                val errorBody = response.errorBody()?.string()
                Log.e(tag, "Error response: $errorBody")
                null
            }
        } catch (e: Exception) {
            Log.e(tag, "Exception in getProductsByCategory", e)
            null
        }
    }
}

