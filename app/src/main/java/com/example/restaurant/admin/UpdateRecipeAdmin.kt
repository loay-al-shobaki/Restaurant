package com.example.restaurant.admin

import BaseFragment
import RecipeImp
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.restaurant.convertToStringWithSemicolon
import com.example.restaurant.database.IRecipeData
import com.example.restaurant.databinding.FragmentUpdateRecipeAdminBinding
import com.example.restaurant.search.model.Recipe
import com.example.restaurant.toList
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch


class UpdateRecipeAdmin : BaseFragment<FragmentUpdateRecipeAdminBinding>() {
    private val recipe_data: UpdateRecipeAdminArgs by navArgs()
    private val recipeImp = RecipeImp()

    override val inflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentUpdateRecipeAdminBinding =
        FragmentUpdateRecipeAdminBinding::inflate


    override fun setUp() {
        setViewsDataFromShowData(recipe_data.recipe)
        Log.d("loay", "setUp:${recipe_data.recipe.price} ")
    }

    override fun addCallBacks() {
        binding.btnsubmit.setOnClickListener {
            UpdateDAta(getNewRecipes())
        }


    }


    fun setViewsDataFromShowData(recipe: Recipe) {
        binding.run {
            Picasso.get().load(recipe.imageUrl).into(imageView)
            editimageUrl.setText(recipe.imageUrl)
            editTextname.setText(recipe.name)
            editTextIngredients.setText(recipe.ingredients.convertToStringWithSemicolon())
            editTextcleanedIngredients.setText(recipe.cleanedIngredients.convertToStringWithSemicolon())
            editTextinstruction.setText(recipe.instruction.convertToStringWithSemicolon())
            editTexttotalTimeInMinutes.setText(recipe.totalTimeInMinutes.toString())
            editTextcuisine.setText(recipe.cuisine)
           ratingBar.rating=recipe.rating
            editTextPrice.setText(recipe.price.toString())

        }
    }

    fun getNewRecipes(): Recipe {
        binding.run {
            return Recipe(
                recipe_data.recipe.id,
                editTextname.text.toString(),
                editTextIngredients.text.toString().toList(),
                editTexttotalTimeInMinutes.text.toString().toInt(),
                editTextcuisine.text.toString(),
                editTextinstruction.text.toString().toList(),
                "",
                editTextcleanedIngredients.text.toString().toList(),
                editimageUrl.text.toString(),
                ratingBar.rating,
                editTextPrice.text.toString().toFloat()
            )
        }
    }

    fun UpdateDAta(recipe: Recipe) {
            lifecycleScope.launch {
                recipeImp.updateDataRecipe(recipe)
                Picasso.get().load(recipe.imageUrl).into(binding.imageView)
            }

    }


}




