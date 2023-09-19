package com.example.restaurant.recipe_details

import BaseFragment
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.restaurant.R


import com.example.restaurant.admin.UpdateRecipeAdminArgs
import com.example.restaurant.database.ShopImp

import com.example.restaurant.databinding.FragmentDetailsBinding
import com.example.restaurant.search.model.DetailsViews
import com.example.restaurant.search.model.Recipe
import com.example.restaurant.search.model.Shop
import com.example.restaurant.toDetailsItem
import com.example.restaurant.util.RecipeViewType
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.launch


class DetailsFragment : BaseFragment<FragmentDetailsBinding>(), TabLayout.OnTabSelectedListener {

    override val inflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentDetailsBinding =
        FragmentDetailsBinding::inflate


    private val data : DetailsFragmentArgs by navArgs()

    private var recipe: Recipe? = null
    private var shopImp= ShopImp()
    private lateinit var recipeDetailsAdapter: RecipeDetailsAdapter
    private var number = 1
    private var price = 0f

    override fun setUp() {


         recipe = data.dataRecipe
       val itemsList: MutableList<DetailsViews<Any>> = mutableListOf()
      itemsList.add(DetailsViews(recipe!!, RecipeViewType.TYPE_HEADER))
      itemsList.addAll(recipe!!.ingredients.map { it.toDetailsItem() })
      recipeDetailsAdapter = RecipeDetailsAdapter(requireContext(),itemsList, this,::onClickButtonadd,::onClickButtonMinus,::onClickButtonAddTOCart)
       binding.recipeDetailsRecycler.adapter = recipeDetailsAdapter
    }

    override fun addCallBacks() {

    }

    private fun onClickButtonadd(btnADD: View):Int{
        return ++number
    }

    private fun onClickButtonMinus(btnMiuns:View):Int{
        return --number
    }

    private  fun onClickButtonAddTOCart(shop:Shop){
        Log.d("loay", "onClickButtonAddTOCart: ${shop}")
        Toast.makeText(requireContext(),"Added successfully",Toast.LENGTH_LONG).show()
        lifecycleScope.launch {
            shopImp.addOrderOfUser(shop)
        }

    }


    override fun onTabSelected(tab: TabLayout.Tab?) {
        binding.apply {
            when (tab?.position) {
                0 -> {
                    val itemsList: MutableList<DetailsViews<Any>> = mutableListOf()
                    itemsList.add(DetailsViews(recipe!!, RecipeViewType.TYPE_HEADER))
                 itemsList.addAll(recipe!!.ingredients.map { it.toDetailsItem() })
                    recipeDetailsAdapter.setSelectedTabData(itemsList)
                }
                1 -> {
                    val itemsList: MutableList<DetailsViews<Any>> = mutableListOf()
                    itemsList.add(DetailsViews(recipe!!, RecipeViewType.TYPE_HEADER))
                   itemsList.addAll(recipe!!.instruction.map { it.toDetailsItem() })
                    recipeDetailsAdapter.setSelectedTabData(itemsList)
                }
            }
        }
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {

    }

    override fun onTabReselected(tab: TabLayout.Tab?) {

    }

    companion object {
        const val RECIPE_OBJECT_PASSING_CODE = "RECIPE"

        fun newInstance(recipe: Recipe) =
            DetailsFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(RECIPE_OBJECT_PASSING_CODE, recipe)
                }
            }
    }
}