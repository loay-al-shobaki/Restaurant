package com.example.restaurant.search

import BaseFragment
import RecipeImp
import android.R
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView

import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController


import com.example.restaurant.databinding.FragmentSearchBinding
import com.example.restaurant.search.model.Recipe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SearchFragment : BaseFragment<FragmentSearchBinding>() {

    private lateinit var ingredientsAdapter: IngredientsSearchAdapter
    private lateinit var dataRecipe: RecipeImp
    private lateinit var currentListRecipe: List<Recipe>
    private lateinit var searshView: SearchView
 //   private lateinit var findRecipesContainsSpecifiedIngredient: FindRecipesContainsSpecifiedIngredientInteractor
    private var searchRecipes: String = "NOT_HAVE_DATA"

    override val inflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSearchBinding =
        FragmentSearchBinding::inflate

    override fun setUp() {
        setupDatasource()
        setUpAdapter(emptyList())
        Log.d("loay", "setUp: 222222")

        searshView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { searchText ->
                    lifecycleScope.launch {
                        val filteredRecipes = getFilteredRecipes(searchText)
                        withContext(Dispatchers.Main) {
                            ingredientsAdapter.setData(filteredRecipes)
                        }
                    }
                }
                return true
            }
        })


    }

    override fun addCallBacks() {
    }

    private fun setupDatasource() {
        searshView = binding.searchView
        dataRecipe = RecipeImp()
    }

    private fun setUpAdapter(recipes: List<Recipe>) {
        ingredientsAdapter = IngredientsSearchAdapter(recipes,::onClickRecipe)
        binding.recyclerSearch.adapter = ingredientsAdapter


    }


    private fun onClickRecipe(recipe:Recipe){
        val action =  SearchFragmentDirections.actionActionSearchToDetailsFragment(recipe)
        findNavController().navigate(action)
    }
    private suspend fun getFilteredRecipes(searchName: String): List<Recipe> {
        return withContext(Dispatchers.IO) {
            val listRecipe = dataRecipe.getRecipeListDatabase()
            listRecipe.filter { recipe ->
                recipe.name.contains(searchName, ignoreCase = true)
            }
        }
    }


}

