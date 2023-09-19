package com.example.restaurant.login

import BaseFragment
import android.app.AlertDialog

import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.navigation.Navigation.findNavController
import com.example.restaurant.R
import com.example.restaurant.database.UserImp
import com.example.restaurant.databinding.FragmentLoginAdminBinding


class LoginAdmin : BaseFragment<FragmentLoginAdminBinding>() {
    val userData = UserImp()
    override val inflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentLoginAdminBinding =
        FragmentLoginAdminBinding::inflate

    override fun setUp() {


    }

    override fun addCallBacks() {
         binding.btnlogin.setOnClickListener {
             findNavController(it).navigate(R.id.action_loginAdmin_to_adminFragment)
         }


    }





}