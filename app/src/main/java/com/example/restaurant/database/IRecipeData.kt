package com.example.restaurant.database

import com.example.restaurant.search.model.Recipe


interface IRecipeData {

    fun addRecioeToDataBase(recipe: Recipe)
    suspend fun getRecipeListDatabase(): List<Recipe>
    suspend fun  updateDataRecipe(recipe: Recipe):Boolean
    suspend  fun deleteDataRecipe(id: String):Boolean
    suspend fun getRecipesWithRatingGreaterThanFour(): List<Recipe>
}