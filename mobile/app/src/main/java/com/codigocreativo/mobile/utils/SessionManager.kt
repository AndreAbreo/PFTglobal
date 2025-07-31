package com.codigocreativo.mobile.utils

import android.content.Context
import com.codigocreativo.mobile.network.User
import com.google.gson.Gson

object SessionManager {
    private const val PREF_NAME = "app_prefs"
    private const val TOKEN_KEY = "jwt_token"
    private const val USER_KEY = "user_data"

    fun saveSessionData(context: Context, token: String, user: User) {
        val sharedPref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPref.edit()

        editor.putString(TOKEN_KEY, token)

        val userJson = Gson().toJson(user)
        editor.putString(USER_KEY, userJson)

        editor.apply()
    }

    fun getToken(context: Context): String? {
        val sharedPref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPref.getString(TOKEN_KEY, null)
    }

    fun getUser(context: Context): User? {
        val sharedPref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val userJson = sharedPref.getString(USER_KEY, null)
        return if (userJson != null) {
            Gson().fromJson(userJson, User::class.java)
        } else {
            null
        }
    }

    fun clearSession(context: Context) {
        val sharedPref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.clear()
        editor.apply()
    }
}
