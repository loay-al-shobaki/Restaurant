package com.example.restaurant.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurant.R
import com.example.restaurant.databinding.ItemListMoseWentedRecipeBinding
import com.example.restaurant.databinding.ItemListRatingRecipeBinding
import com.example.restaurant.search.model.Recipe
import com.example.restaurant.databinding.ItemrandomrecipelistBinding
import com.example.restaurant.localData.domin.HomeItem
import com.example.restaurant.localData.domin.HomeItemType
import com.example.restaurant.search.model.Shop

class HomeAdpter(

   private val items: List<HomeItem<Any>>,
    private var onClickRecipe: (recipe: Recipe?) -> Unit,
) :
    RecyclerView.Adapter<HomeAdpter.BasicViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasicViewHolder {
        return when (viewType) {
            VIEW_TYPE_MAIN_RECIPE -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.itemrandomrecipelist, parent, false)
                mainRecipeViewHolder(view)
            }

            VIEW_TYPE_RATING_RECIPE -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_list_rating_recipe, parent, false)
                ratingRecipesViewHolder(view)
            }

            VIEW_TYPE_POST -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_list_mose_wented_recipe, parent, false)
                mostneddSectionViewHolder(view)
            }

            else -> throw Exception("UNKNOWN VIEW TYPE")
        }
       }


    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: BasicViewHolder, position: Int) {
        when (holder) {
            is mainRecipeViewHolder -> bindMainRecipe(holder, position)
            is ratingRecipesViewHolder -> bindRatingRecipe(holder, position)
            is mostneddSectionViewHolder -> bindMostNeedRecipe(holder, position)
        }

    }

    sealed class BasicViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    class mainRecipeViewHolder(itemView: View) : BasicViewHolder(itemView) {
        val binding = ItemrandomrecipelistBinding.bind(itemView)
    }

    class ratingRecipesViewHolder(itemView: View) : BasicViewHolder(itemView) {
    val binding = ItemListRatingRecipeBinding.bind(itemView)
    }

    class mostneddSectionViewHolder(itemView: View) : BasicViewHolder(itemView) {
        val binding = ItemListMoseWentedRecipeBinding.bind(itemView)
    }

    private  fun bindMainRecipe(holder: HomeAdpter.mainRecipeViewHolder, position: Int) {
        val recipelList = items[position].item as List<Recipe>
        val adapter = MainRecipesAdapter(recipelList, onClickRecipe)
        holder.binding.recyclerViewRecipes.adapter = adapter
    }


    private fun bindRatingRecipe(holder: ratingRecipesViewHolder, position: Int) {
        val recipelList = items[position].item as List<Recipe>
        val adapter = RatinRecipeAdpter(recipelList,onClickRecipe)
        holder.binding.recyclerViewRatingRecipe.adapter=adapter
    }

    private fun bindMostNeedRecipe(holder: mostneddSectionViewHolder, position: Int) {
        val shoplList = items[position].item as List<Shop>
        val adapter = MostWantedRecipeAdpter(shoplList,onClickRecipe)
        holder.binding.recyclerViewRatingRecipe.adapter=adapter
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position].type) {
            HomeItemType.TYPE_MAIN_RECIPE -> VIEW_TYPE_MAIN_RECIPE
            HomeItemType.TYPE_RATING_RECIPE -> VIEW_TYPE_RATING_RECIPE
            HomeItemType.TYPE_MOSTNEED_RECIPE -> VIEW_TYPE_POST
        }
    }

    companion object {
         const val VIEW_TYPE_MAIN_RECIPE = 1
         const val VIEW_TYPE_RATING_RECIPE = 2
         const val VIEW_TYPE_POST =4
    }
}





























//    override fun onBindViewHolder(holder: ProductViewHoloder, position: Int) {
//        val recipelList = list[position]
//        holder.bindingItem.also {
//            Picasso.get().load(recipelList.imageUrl.trim()).into(it.dishOfTheDayImage)
//            it.textViewRecipeName.text = recipelList.name
//            it.textViewRecipeCookingTime.text = recipelList.totalTimeInMinutes.toString()
//            it.textViewRecipeCuisine.text = recipelList.cuisine
//            it.root.setOnClickListener {
//                onClickRecipe(recipelList)
//            }
//        }
//    }