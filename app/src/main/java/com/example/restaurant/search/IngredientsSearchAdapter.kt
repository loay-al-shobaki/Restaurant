package com.example.restaurant.search

import android.content.DialogInterface.OnClickListener
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.restaurant.R
import com.example.restaurant.databinding.ItemRecipeCategoryBinding
import com.example.restaurant.search.model.Recipe
import com.squareup.picasso.Picasso

class IngredientsSearchAdapter(
    private var recipes: List<Recipe>,
   private var onClickRecipeListener:(recipe:Recipe) -> Unit
    ) : RecyclerView.Adapter<IngredientsSearchAdapter.RecipeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        return RecipeViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_recipe_category, parent, false)
        )
    }

    fun setData(newRecipes: List<Recipe>) {
        recipes = newRecipes
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = recipes[position]
        holder.binding.apply {
            textViewRecipeName.text = recipe.name
            textViewTotalTime.text = "${recipe.totalTimeInMinutes} min"
            textViewIngredientsCount.text = "${recipe.ingredients.size} ingredients"
            Picasso.get().load(recipe.imageUrl).into(imageViewRecipe)
            root.setOnClickListener { onClickRecipeListener(recipe)}
        }
    }

    override fun getItemCount() = recipes.size


    class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemRecipeCategoryBinding.bind(itemView)
    }
}