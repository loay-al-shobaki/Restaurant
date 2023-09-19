package com.example.restaurant.admin

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurant.R
import com.example.restaurant.RecipeInteractionListener
import com.example.restaurant.search.model.Recipe
import com.example.restaurant.databinding.ItemRecipeUpdateBinding
import com.squareup.picasso.Picasso

class ShowRecipeAdminAdpter(private var list: List<Recipe>, private  val listener:RecipeInteractionListener) :
    RecyclerView.Adapter<ShowRecipeAdminAdpter.ProductViewHoloder>() {
    inner class ProductViewHoloder( viewItem: View) : RecyclerView.ViewHolder(viewItem){
        val binding = ItemRecipeUpdateBinding.bind(viewItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHoloder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recipe_update,parent,false)
        return ProductViewHoloder(view)
    }

    override fun getItemCount() = list.size

    fun setData(newList:List<Recipe>){
        list =newList
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ProductViewHoloder, position: Int) {
        val currentRecipel = list[position]
        holder.binding.also {
            Picasso.get().load(currentRecipel.imageUrl).into(it.imageViewQuickRecipe)
            it.textViewQuickRecipesName.text=currentRecipel.name

            it.root.setOnClickListener {
                listener.onClickItem(currentRecipel)
            }
            it.imgDelete.setOnClickListener {
                listener.onClickDeleteRecipe(currentRecipel.id)
            }


        }
    }


}

