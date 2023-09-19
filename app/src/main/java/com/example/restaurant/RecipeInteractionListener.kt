package com.example.restaurant

import android.view.View
import com.example.restaurant.search.model.Recipe

interface RecipeInteractionListener {
    fun onClickItem(recipe: Recipe)
    fun onClickDeleteRecipe(id:String)

}