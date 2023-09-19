package com.example.restaurant.ingredients

import BaseFragment
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.restaurant.R
import com.example.restaurant.databinding.FragmentIngredientsBinding
import com.example.restaurant.databinding.FragmentLoginBinding


class IngredientsFragment :  BaseFragment<FragmentIngredientsBinding>() {

    override val inflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentIngredientsBinding =
        FragmentIngredientsBinding::inflate

    override fun setUp() {
    }

    override fun addCallBacks() {


    }


}