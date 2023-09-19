package com.example.restaurant.database

import android.util.Log
import com.example.restaurant.search.model.Recipe
import com.example.restaurant.search.model.Shop
import com.example.restaurant.toMap
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class ShopImp : IShopData {

    companion object {
        const val COLLECTION_SHOP = "shop"
        const val FIELD_ID_USER = "IdUser"
        const val FIELD_RECIPE_NAME = "recipeName"
        const val FIELD_URL_RECPE = "UrlOfRecipe"
        const val FIELD_PRICE = "price"
        const val FIELD_COUNT_OF_RECPE = "countOfRecipe"

    }

    val db = Firebase.firestore
    val collection = db.collection(COLLECTION_SHOP)

    override suspend fun addOrderOfUser(shop: Shop) {
        collection
            .add(shop.toMap())
            .addOnSuccessListener {
                Log.d("loay", "User added successfully!")
            }
            .addOnFailureListener { e ->
                Log.w("loay", "Error adding user", e)
            }
    }

    override suspend fun getAllOrderOfUserById(id: String): List<Shop> {
        val shops = mutableListOf<Shop>()

          val querySnapshot=  collection.whereEqualTo(FIELD_ID_USER, id)
            .get()
            .await()

        for (document in querySnapshot.documents) {
            val shop = document.toObject(Shop::class.java)
            shop?.let {
                shops.add(it)
            }
        }
        return shops
    }



    override suspend fun getAllRecipeTheMostWanted(): List<Shop> {
        val recipesList = mutableListOf<Shop>()
        val recipeMap = mutableMapOf<String, Int>()

        collection.get().addOnSuccessListener { querySnapshot ->
            for (document in querySnapshot) {
                val recipe = document.toObject(Shop::class.java)
                val recipeName = recipe.recipeName

                // Increment the count for the recipe name in the map
                val count = recipeMap.getOrDefault(recipeName, 0)
                recipeMap[recipeName] = count + 1

                if (count == 0) {
                    // Only add the recipe to the list if it's the first occurrence
                    recipesList.add(recipe)
                }
            }

            // Sort the recipesList based on the count of each recipe name in descending order
            recipesList.sortByDescending { recipeMap[it.recipeName] }

        }.addOnFailureListener { exception ->

        }.await()

        return recipesList
    }



    override suspend fun deleteItemShopById(id:String): Boolean {
         return try {
             collection.document(id).delete().await()
             true
         } catch (e: Exception) {
             false
         }
    }






}
