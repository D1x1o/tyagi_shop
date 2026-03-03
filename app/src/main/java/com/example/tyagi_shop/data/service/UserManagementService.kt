package com.example.tyagi_shop.data.service

import com.example.tyagi_shop.data.model.*
import retrofit2.Response
import retrofit2.http.*

const val API_KEY =
    "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InFqaXd1Z2lkaWRremxlcXpvdG1wIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NzI0Nzk4MDMsImV4cCI6MjA4ODA1NTgwM30.JiYMz7leavCGvs5biLAVYjB_V0AhoHgIbWWpffSa6RQ"



interface UserManagementService {
    @Headers("apikey: $API_KEY", "Content-Type: application/json")
    @POST("auth/v1/signup")
    suspend fun signUp(@Body signUpRequest: SignUpRequest): Response<SignUpResponse>

    @Headers("apikey: $API_KEY", "Content-Type: application/json")
    @POST("auth/v1/token?grant_type=password")
    suspend fun signIn(@Body signInRequest: SignInRequest): Response<SignInResponse>

    @Headers("apikey: $API_KEY", "Content-Type: application/json")
    @POST("auth/v1/verify")
    suspend fun verifyOTP(@Body verifyOtpRequest: VerifyOtpRequest): Response<Any>
    @Headers("apikey: $API_KEY", "Content-Type: application/json")
    @POST("auth/v1/recover")
    suspend fun recoverPassword(@Body body: Map<String, String>): Response<Any>

    @Headers("apikey: $API_KEY", "Content-Type: application/json")
    @POST("change-password")
    suspend fun changePassword(@Body body: ChangePasswordRequest): Response<Any>
}
