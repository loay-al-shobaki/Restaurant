package com.example.restaurant.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import com.example.restaurant.R
import com.example.restaurant.databinding.ItemRandomRecipeBinding
import com.example.restaurant.search.model.Recipe
import com.squareup.picasso.Picasso

class MainRecipesAdapter(
    private val recipes: List<Recipe>,
    private val onClickRecipe: (recipe: Recipe) -> Unit,
) :
    RecyclerView.Adapter<MainRecipesAdapter.RecipeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_random_recipe, parent, false)
        return RecipeViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val currentItem = recipes[position]
        holder.binding.apply {
            textViewRecipeName.text = currentItem.name
            textViewRecipeCuisine.text =currentItem.cuisine
            textViewRecipeCookingTime.text=currentItem.totalTimeInMinutes.toString()
            Picasso.get().load(currentItem.imageUrl).into(dishOfTheDayImage)
            root.setOnClickListener { onClickRecipe(currentItem) }
        }
    }



    override fun getItemCount() = recipes.size

    class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemRandomRecipeBinding.bind(itemView)
    }
}