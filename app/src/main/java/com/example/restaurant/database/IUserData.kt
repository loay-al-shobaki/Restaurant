package com.example.restaurant.database

import com.example.restaurant.search.model.Users

interface IUserData {
    fun addUserToDataBase(user: Users)
    suspend fun getIdIfUersIfExist(email: String, password: String): String
    suspend fun updateUserdata(oldid: String, user: Users, passwordConform:String): Boolean
    suspend fun getDataUserDatabaseById(id:String): Users
}