package com.anggiiqna.polafit.network.datamodel

class HistoryRequest (
    val userId: Int,
    val foodName: String,
    val serving: Int,
    val calories: Int,
    val protein: Int,
    val fat: Int,
    val carbs: Int,
    val fiber: Int,
    val sugar: Int,
    val image: String
)