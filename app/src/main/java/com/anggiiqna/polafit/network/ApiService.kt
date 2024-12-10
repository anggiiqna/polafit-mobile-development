package com.anggiiqna.polafit.network

import com.anggiiqna.polafit.network.datamodel.LoginRequest
import com.anggiiqna.polafit.network.datamodel.LoginResponse
import com.anggiiqna.polafit.network.datamodel.RegisterRequest
import com.anggiiqna.polafit.network.datamodel.RegisterResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @POST("register")
    suspend fun register(@Body request: RegisterRequest): RegisterResponse
}
