package com.example.restaurant.database

import android.provider.ContactsContract.CommonDataKinds.Email
import com.example.restaurant.search.model.Shop

interface IShopData {

    suspend fun addOrderOfUser(shop:Shop)
    suspend fun getAllOrderOfUserById(id:String):List<Shop>
    suspend fun getAllRecipeTheMostWanted():List<Shop>
    suspend fun deleteItemShopById(id:String): Boolean
}