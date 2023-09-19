package com.example.restaurant.home

import BaseFragment
import RecipeImp
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.restaurant.database.ShopImp
import com.example.restaurant.databinding.FragmentHomeBinding
import com.example.restaurant.localData.domin.HomeItem
import com.example.restaurant.localData.domin.HomeItemType
import com.example.restaurant.search.model.Recipe
import com.example.restaurant.search.model.Shop
import kotlinx.coroutines.launch


class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    lateinit var recipeImp: RecipeImp
    lateinit var shopImp: ShopImp
    override val inflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding =
        FragmentHomeBinding::inflate


    override fun setUp() {
        recipeImp = RecipeImp()
        shopImp= ShopImp()
        lifecycleScope.launch {
            val itemList:MutableList<HomeItem<Any>> = mutableListOf()
            itemList.add(HomeItem(recipeImp.getRecipeListDatabase(),HomeItemType.TYPE_MAIN_RECIPE))
            itemList.add(HomeItem(recipeImp.getRecipesWithRatingGreaterThanFour(),HomeItemType.TYPE_RATING_RECIPE))
            itemList.add(HomeItem(shopImp.getAllRecipeTheMostWanted(),HomeItemType.TYPE_MOSTNEED_RECIPE))
            val adpter = HomeAdpter(itemList,::onClickRecipe)
            binding.recyclerviewHome.adapter = adpter


            Log.d("loay", "xxx: ${  recipeImp.getRecipeByName("Mexican Style Black Bean Burrito Recipe")}")

        }



    }

    override fun addCallBacks() {


    }

    private fun onClickRecipe(recipe: Recipe?) {
        val action  = HomeFragmentDirections.actionActionHomeToDetailsFragment(recipe)
        findNavController().navigate(action)
    }



}

















