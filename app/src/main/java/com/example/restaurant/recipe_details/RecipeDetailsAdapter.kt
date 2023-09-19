package com.example.restaurant.recipe_details

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurant.R
import com.example.restaurant.databinding.ItemDetailsBinding
import com.example.restaurant.databinding.ItemHeaderRecipeDetailsBinding
import com.example.restaurant.localData.SharedPrefs
import com.example.restaurant.search.model.DetailsViews
import com.example.restaurant.search.model.Recipe
import com.example.restaurant.search.model.Shop
import com.example.restaurant.util.RecipeViewType
import com.google.android.material.tabs.TabLayout
import com.squareup.picasso.Picasso

class RecipeDetailsAdapter(
    private val context: Context,
    private var items: List<DetailsViews<Any>>,
    private var listner: TabLayout.OnTabSelectedListener,
    private var onClickButtonadd: (btn: View) -> Int,
    private var onClickButtonMins: (btn: View) -> Int,
    private var onClickAddTOCart: (shop:Shop) -> Unit,

    ) :
    RecyclerView.Adapter<RecipeDetailsAdapter.BasicViewHolder>() {
    sealed class BasicViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    fun setSelectedTabData(newItems: List<DetailsViews<Any>>) {
        items = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BasicViewHolder {
        return when (viewType) {
            TYPE_HEADER -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_header_recipe_details, parent, false)
                RecipeDetailsHeaderViewHolder(view)
            }

            ITEM_INGREDIENT -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_details, parent, false)
                IngredientsAndInstructionsDetailsViewHolder(view)
            }

            else -> throw Exception("UNKNOWN VIEW TYPE")
        }
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(
        holder: BasicViewHolder,
        position: Int
    ) {
        when (holder) {
            is RecipeDetailsHeaderViewHolder -> bindHeader(holder, position)
            is IngredientsAndInstructionsDetailsViewHolder -> bindDetails(holder, position)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun bindHeader(holder: RecipeDetailsHeaderViewHolder, position: Int) {
        val currentItem = items[position].item as Recipe
        holder.binding.apply {
            tabLayoutIngredientsInstructions.addOnTabSelectedListener(listner)
            textViewRecipeName.text = currentItem.name
            textViewNativeCountry.text = currentItem.cuisine
            textViewTimeRequired.text = "${currentItem.totalTimeInMinutes} min"
            textViewNumberOfIngredients.text = "${currentItem.ingredients.size} ingredients"
            textPrice.text = "$" + currentItem.price.toString()
            var BasePrice = currentItem.price
            Picasso.get().load(currentItem.imageUrl).into(imageViewRecipe)

            btnPlus.setOnClickListener {
                var count = onClickButtonadd(it)
                txtCount.text = count.toString()
                textPrice.text = "$" + (count * BasePrice).toString()
            }
            btnMinus.setOnClickListener {
                var count = onClickButtonMins(it)
                txtCount.text = count.toString()
                textPrice.text = "$" + (count * BasePrice).toString()
            }
            var isButtonClicked = false
            btnAddToCary.setOnClickListener {
                if (!isButtonClicked) {
                    var shop = Shop("",
                        SharedPrefs.getUserIdFromPrefs(context),
                        currentItem.name,
                        currentItem.imageUrl,
                        textPrice.text.toString().substring(1).toFloat(),
                        txtCount.text.toString().toInt()
                    )
                    onClickAddTOCart(shop)
                    isButtonClicked = true
                }
            }

        }
    }


    private fun bindDetails(holder: IngredientsAndInstructionsDetailsViewHolder, position: Int) {
        val currentItem = items[position].item as String
        holder.binding.textViewIngredientInstruction.text = currentItem
    }

    class RecipeDetailsHeaderViewHolder(itemView: View) : BasicViewHolder(itemView) {
        val binding = ItemHeaderRecipeDetailsBinding.bind(itemView)
    }

    class IngredientsAndInstructionsDetailsViewHolder(itemView: View) : BasicViewHolder(itemView) {
        val binding = ItemDetailsBinding.bind(itemView)
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position].type) {
            RecipeViewType.TYPE_HEADER -> TYPE_HEADER
            RecipeViewType.ITEM_INGREDIENT -> ITEM_INGREDIENT
        }
    }

    companion object {
        const val TYPE_HEADER = 1
        const val ITEM_INGREDIENT = 2
    }
}