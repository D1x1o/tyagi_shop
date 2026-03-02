package com.example.tyagi_shop.data.service

import com.example.tyagi_shop.data.model.*
import retrofit2.Response
import retrofit2.http.*

const val API_KEY =
    "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImRidG9uaWxtYnd6d3hxYXprY2hsIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NzI0NDkyNTIsImV4cCI6MjA4ODAyNTI1Mn0.U43xM7fKBojgik6lm_c3R_DhGoed-jfd9rj1muEC6Pc"



interface UserManagementService {
    @Headers("apikey: $API_KEY", "Content-Type: " +
            "application/json")
    @POST("auth/v1/signup")
    suspend fun signUp(@Body signUpRequest: SignUpRequest): Response<SignUpResponse>

    @Headers("apikey: $API_KEY", "Content-Type: application/json")
    @POST("auth/v1/token?grant_type=password")
    suspend fun signIn(@Body signInRequest: SignInRequest): Response<SignInResponse>
}
