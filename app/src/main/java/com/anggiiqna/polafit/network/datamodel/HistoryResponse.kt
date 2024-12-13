package com.anggiiqna.polafit.network.datamodel
import com.google.gson.annotations.SerializedName

class HistoryResponse (
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("name")
    val foodName: String,
    @SerializedName("serving")
    val serving: Int,
    @SerializedName("calories")
    val calories: Int,
    @SerializedName("protein")
    val protein: Int,
    @SerializedName("fat")
    val fat: Int,
    @SerializedName("carbs")
    val carbs: Int,
    @SerializedName("fiber")
    val fiber: Int,
    @SerializedName("sugar")
    val sugar: Int,
    @SerializedName("image")
    val image: String,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("message")
    val message: String,
    val isSuccessful: Boolean
)