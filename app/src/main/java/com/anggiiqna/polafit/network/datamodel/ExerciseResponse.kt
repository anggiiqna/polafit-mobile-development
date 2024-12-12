package com.anggiiqna.polafit.network.datamodel
import com.google.gson.annotations.SerializedName

class ExerciseResponse (
    @SerializedName("recommended_exercise") val recommendations: String
)