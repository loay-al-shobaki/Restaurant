package com.example.restaurant.localData

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.navigation.fragment.findNavController
import com.example.restaurant.R
import com.example.restaurant.search.model.Users

object SharedPrefs{
    private lateinit var sharedPrefences: SharedPreferences
      private const val NAME_PREFS="DataPrefs"


    fun saveUserDataToPrefs(context: Context, user: Users) {
        val sharedPreferences = context.getSharedPreferences(NAME_PREFS, MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("name", user.name)
        editor.putString("email", user.email)
        editor.putString("password", user.password)
        editor.apply()
    }
    fun saveUserIdoPrefs(context: Context, id: String) {
        val sharedPreferences = context.getSharedPreferences(NAME_PREFS, MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("id", id)
        editor.apply()
    }

    fun saveUseurlImgProfile(context: Context, urlImgProfile: String) {
        val sharedPreferences = context.getSharedPreferences(NAME_PREFS, MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("urlImgProfile", urlImgProfile)
        editor.apply()
    }

    fun getUserIdFromPrefs(context: Context): String {
        val sharedPreferences = context.getSharedPreferences(NAME_PREFS, MODE_PRIVATE)
        val id = sharedPreferences.getString("id", "")
        return id!!
    }

    fun getUserDataFromPrefs(context: Context): Users {
        val sharedPreferences = context.getSharedPreferences(NAME_PREFS, MODE_PRIVATE)
        val name = sharedPreferences.getString("name", "")
        val email = sharedPreferences.getString("email", "")
        val password = sharedPreferences.getString("password", "")
        val dateOfBirth = sharedPreferences.getString("dateOfBirth", "")
        val location = sharedPreferences.getString("location", "")
        return Users("", name!!, email!!, password!!,dateOfBirth!!,location!!)
    }
    fun isTheUserIdIsFound(context: Context):Boolean{
        var vlag =false
        val sharedPreferences = context.getSharedPreferences(NAME_PREFS, MODE_PRIVATE)
        val id = sharedPreferences.getString("id", "")
        if (id!!.length>1){
            vlag =true
        }
        return vlag
    }



    fun clearTextInSharedPreferences(context: Context) {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(NAME_PREFS, Context.MODE_PRIVATE)
        sharedPreferences.edit().clear().apply()
    }





}

