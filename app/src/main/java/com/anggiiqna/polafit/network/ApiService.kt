package com.anggiiqna.polafit.network

import com.anggiiqna.polafit.network.datamodel.LoginRequest
import com.anggiiqna.polafit.network.datamodel.LoginResponse
import com.anggiiqna.polafit.network.datamodel.RegisterRequest
import com.anggiiqna.polafit.network.datamodel.RegisterResponse
import com.anggiiqna.polafit.network.datamodel.UserResponse
import com.anggiiqna.polafit.network.datamodel.UserRequest
import retrofit2.http.Path
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.GET
import retrofit2.http.PUT

interface ApiService {
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @POST("auth/register")
    suspend fun register(@Body request: RegisterRequest): RegisterResponse

    @GET("auth/user/{id}")
    suspend fun getUserById(@Path("id") id: String): UserResponse

    @PUT("auth/user/{id}")
    suspend fun updateUserProfile(@Path("id") id: String, @Body user: UserRequest): UserRequest
}
