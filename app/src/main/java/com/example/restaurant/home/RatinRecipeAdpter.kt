package com.example.restaurant.home

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.restaurant.BaseAdpter
import com.example.restaurant.databinding.ItemRatingRecipeBinding
import com.example.restaurant.search.model.Recipe
import com.squareup.picasso.Picasso

class RatinRecipeAdpter(
    private val list: List<Recipe>,
    private val onClickRecipe: (recipe: Recipe) -> Unit,
) :
    BaseAdpter<ItemRatingRecipeBinding, Recipe>(list) {
    override val bindingInFalter: (LayoutInflater, ViewGroup, Boolean) -> ItemRatingRecipeBinding
        get() = ItemRatingRecipeBinding::inflate

    override fun bindItem(binding: ItemRatingRecipeBinding, item: Recipe) {
        with(binding) {
            textViewQuickRecipesName.text = item.name
            ratingBar2.rating = item.rating
            Picasso.get().load(item.imageUrl).into(imageViewQuickRecipe)
            root.setOnClickListener {
                onClickRecipe(item)
            }
        }
    }

}