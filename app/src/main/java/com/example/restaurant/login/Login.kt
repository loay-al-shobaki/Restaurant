package com.example.restaurant.login

import BaseFragment
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.restaurant.R
import com.example.restaurant.database.UserImp
import com.example.restaurant.databinding.FragmentLoginBinding
import com.example.restaurant.localData.SharedPrefs
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Login : BaseFragment<FragmentLoginBinding>() {
    val userData = UserImp()
    lateinit var email: String
    lateinit var password: String
     private lateinit var mNavController:NavController
    override val inflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentLoginBinding =
        FragmentLoginBinding::inflate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (SharedPrefs.isTheUserIdIsFound(requireContext())) {
            findNavController().navigate(R.id.action_login_to_homeActivity)

        }
    }

    override fun setUp() {
        mNavController=findNavController()
    }

    override fun addCallBacks() {
        binding.apply {
            btnlogin.setOnClickListener { v ->
                setupInformaionUser()

                lifecycleScope.launch {
                    val id = getIdIfUserExistsInDatabase(email, password)
                    if (id.isEmpty()) {
                        txtEmail.error = "Wrong email or password"
                    } else {
                        SharedPrefs.saveUserIdoPrefs(requireContext(), id)
                        var user = userData.getDataUserDatabaseById(id)
                        SharedPrefs.saveUserDataToPrefs(requireContext(), user)
                        findNavController(v).navigate(R.id.action_login_to_homeActivity)

                    }
                }
            }
        }
        binding.textViewSignup.setOnClickListener {
            mNavController.navigate(R.id.action_login_to_signup)
        }
        binding.txtAdmin.setOnClickListener {
            mNavController.navigate(R.id.action_login_to_loginAdmin)
        }

    }


    fun setupInformaionUser() {
        binding.apply {
            email = txtEmail.text.toString()
            password = txtPassword.text.toString()
        }
    }

    fun isValidEmailAndPassword(id: String): Boolean {
        return id.isNotEmpty()
    }

    suspend fun getIdIfUserExistsInDatabase(email: String, password: String): String {

        return userData.getIdIfUersIfExist(email, password)

    }


}