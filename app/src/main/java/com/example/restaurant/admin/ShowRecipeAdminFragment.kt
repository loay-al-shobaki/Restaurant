package com.example.restaurant.admin

import BaseFragment
import RecipeImp
import android.content.DialogInterface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.restaurant.R
import com.example.restaurant.RecipeInteractionListener
import com.example.restaurant.databinding.FragmentShowAdminRecipeBinding
import com.example.restaurant.search.model.Recipe
import com.example.restaurant.showConfirmationDialog
import kotlinx.coroutines.launch


class ShowRecipeAdminFragment : BaseFragment<FragmentShowAdminRecipeBinding>(),
    RecipeInteractionListener {
    val recipeData = RecipeImp()
    lateinit var recipe: Recipe
    lateinit var adpter: ShowRecipeAdminAdpter
    lateinit var id: String

    override val inflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentShowAdminRecipeBinding =
        FragmentShowAdminRecipeBinding::inflate

    override fun setUp() {
        lifecycleScope.launch {
            adpter = ShowRecipeAdminAdpter(
                recipeData.getRecipeListDatabase(),
                this@ShowRecipeAdminFragment
            )
            binding.recyclerUpdateRecip.adapter = adpter

        }


    }

    override fun addCallBacks() {
      binding.btnFloatAction.setOnClickListener {
          findNavController().navigate(R.id.action_showRecipeAdminFragment_to_EntrtDataadminFragment)
      }
    }

    override fun onClickItem(recipe: Recipe) {
        Log.d("loay", "setUp:${recipe.price} ff")
        val action =
            ShowRecipeAdminFragmentDirections.actionShowRecipeAdminFragmentToUpdateRecipeAdmin(
                recipe
            )
        findNavController().navigate(action)
    }

    override fun onClickDeleteRecipe(id: String) {
        this.id = id
        context?.showConfirmationDialog(
            "Confirmation",
            "Are you sure you want to delete?",
            positiveButtonListener,
            negativeButtonListener
        )
    }


    private val positiveButtonListener =
        DialogInterface.OnClickListener { dialog, which ->

            lifecycleScope.launch {
                recipeData.deleteDataRecipe(id)
                adpter.setData(recipeData.getRecipeListDatabase())
            }
        }

    private val negativeButtonListener =
        DialogInterface.OnClickListener { dialog, which ->

    }


}