package com.example.restaurant.Cart

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurant.R
import com.example.restaurant.databinding.ItemCartBinding
import com.example.restaurant.databinding.ItemRecipeCategoryBinding
import com.example.restaurant.search.model.Recipe
import com.example.restaurant.search.model.Shop
import com.squareup.picasso.Picasso

class CartAdapter(
    private var shopList: List<Shop>,
    private val listenerCart:listenerCart
    ) : RecyclerView.Adapter<CartAdapter.RecipeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        return RecipeViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_cart, parent, false)
        )
    }

    fun setData(newList:List<Shop>){
        shopList =newList
        notifyDataSetChanged()
    }


    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val shop = shopList[position]
        holder.binding.apply {
            Picasso.get().load(shop.UrlOfRecipe).into(imgRecipe)
            txtName.text=shop.recipeName
            txtPrice.text="$"+shop.price.toString()
            txtCountOfRecipe.text=shop.countOfRecipe.toString()

            imgClose.setOnClickListener {
                listenerCart.onClickDeleteShop(shop.id)
            }
            imgMap.setOnClickListener {
                listenerCart.onCliceLocation(shop)
            }



        }
    }

    override fun getItemCount() = shopList.size


    class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemCartBinding.bind(itemView)
    }
}