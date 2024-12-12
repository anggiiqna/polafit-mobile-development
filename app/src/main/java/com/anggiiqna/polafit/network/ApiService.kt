package com.anggiiqna.polafit.network

import com.anggiiqna.polafit.network.datamodel.ExerciseRequest
import com.anggiiqna.polafit.network.datamodel.ExerciseResponse
import com.anggiiqna.polafit.network.datamodel.HistoryRequest
import com.anggiiqna.polafit.network.datamodel.HistoryResponse
import com.anggiiqna.polafit.network.datamodel.LoginRequest
import com.anggiiqna.polafit.network.datamodel.LoginResponse
import com.anggiiqna.polafit.network.datamodel.RegisterRequest
import com.anggiiqna.polafit.network.datamodel.RegisterResponse
import com.anggiiqna.polafit.network.datamodel.UserRequest
import com.anggiiqna.polafit.network.datamodel.UserResponse
import retrofit2.http.Path
import retrofit2.http.Part
import retrofit2.http.Multipart
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
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

    @Multipart
    @PUT("auth/user/{id}")
    suspend fun updateUserProfileWithImage(
        @Path("id") id: String,
        @Part username: MultipartBody.Part,
        @Part email: MultipartBody.Part,
        @Part phone: MultipartBody.Part,
        @Part image: MultipartBody.Part
    ): UserRequest

    @Multipart
    @PUT("auth/user/{id}")
    suspend fun updateUserProfileWithoutImage(
        @Path("id") id: String,
        @Part username: MultipartBody.Part,
        @Part email: MultipartBody.Part,
        @Part phone: MultipartBody.Part,
    ): UserRequest

    @POST("/predict_exercise")
    suspend fun getExerciseRecommendation(@Body request: ExerciseRequest): ExerciseResponse

    @POST("/predict_food")
    @Multipart
    suspend fun predictFood(
        @Part file: MultipartBody.Part
    ): Response<ScanResponse>

    @Multipart
    @POST("/food-history")
    suspend fun storeFoodHistory(
        @Part("userId") userId: RequestBody,
        @Part("foodName") foodName: RequestBody,
        @Part("serving") serving: RequestBody,
        @Part("calories") calories: RequestBody,
        @Part("protein") protein: RequestBody,
        @Part("fat") fat: RequestBody,
        @Part("carbs") carbs: RequestBody,
        @Part("fiber") fiber: RequestBody,
        @Part("sugar") sugar: RequestBody,
        @Part image: MultipartBody.Part?
    ): HistoryResponse

    @GET("food-history/{id}")
    suspend fun getHistoryByUserId(@Path("id") userId: String): List<HistoryResponse>
}
