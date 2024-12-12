package com.anggiiqna.polafit.pref

import android.content.Context
import android.content.SharedPreferences

class AppPreferences(private val context: Context) {

    private val pref: SharedPreferences = context.getSharedPreferences("app_pref", Context.MODE_PRIVATE)
    private val edit: SharedPreferences.Editor = pref.edit()

    fun saveToken(token: String) {
        pref.edit().putString("token", token).apply()
    }

    fun clearToken() {
        pref.edit().remove("token").apply()
    }

    fun getToken(): String? {
        return pref.getString("token", null)
    }

    fun isTokenAvailable(): Boolean {
        return !getToken().isNullOrBlank()
    }
}