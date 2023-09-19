package com.example.restaurant.home

import RecipeImp
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.restaurant.BaseAdpter
import com.example.restaurant.databinding.ItemRecipeMostWantedBinding
import com.example.restaurant.search.model.Recipe
import com.example.restaurant.search.model.Shop
import com.squareup.picasso.Picasso
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.math.log

class MostWantedRecipeAdpter(val list: List<Shop>, val onClickItem: (recipe: Recipe?) -> Unit) :
    BaseAdpter<ItemRecipeMostWantedBinding, Shop>(list) {
    val recipeImp = RecipeImp()

    override val bindingInFalter: (LayoutInflater, ViewGroup, Boolean) -> ItemRecipeMostWantedBinding
        get() = ItemRecipeMostWantedBinding::inflate

    @OptIn(DelicateCoroutinesApi::class)
    override fun bindItem(binding: ItemRecipeMostWantedBinding, item: Shop) {
        with(binding) {
            textViewQuickRecipesName.text = item.recipeName
            Picasso.get().load(item.UrlOfRecipe).into(imageViewQuickRecipe)
            root.setOnClickListener {
                val UrlOfRecipe = item.UrlOfRecipe
                GlobalScope.launch(Dispatchers.Main){
                    val recipe = recipeImp.getRecipeByName(UrlOfRecipe)
                    if (recipe != null) {
                        onClickItem(recipe)
                    } else {
                        // Handle the case when the recipe is null
                    }
                }

            }

        }
    }
}
