package com.example.restaurant.login

import BaseFragment
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import androidx.navigation.Navigation
import com.example.restaurant.R
import com.example.restaurant.database.UserImp

import com.example.restaurant.databinding.FragmentSignupBinding
import com.example.restaurant.localData.SharedPrefs
import com.example.restaurant.search.model.Users


class Signup() : BaseFragment<FragmentSignupBinding>() {
    lateinit var userData: UserImp
    lateinit var name: String
    lateinit var email: String
    lateinit var password: String

    override val inflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSignupBinding =
        FragmentSignupBinding::inflate

    override fun setUp() {
        userData = UserImp()

    }

    override fun addCallBacks() {
        binding.apply {

            btnSignIn.setOnClickListener {
                setupInformaionUser()
                if (!isEmailValid(email) && !isEmailPasswoedValid(password)) {
                    txtEmail.error = "Email must be valid"
                } else if (!isEmailPasswoedValid(password)) {
                    txtPassword.error = "Password must be more than 7 characters"
                } else {
                    val user = Users(
                        "", name,
                        email,
                        password,
                        "",
                        "",

                    )
                    userData.addUserToDataBase(user = user)
                    Navigation.findNavController(it).navigate(R.id.action_signup_to_login)
                }


            }
        }



        binding.textViewLogin.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_signup_to_login)
        }


    }

    fun isEmailValid(email: String): Boolean {
        val regex = Regex("^([\\w\\.-]+)@([\\w\\.-]+)\\.([a-zA-Z]{2,})$")
        return email.matches(regex)
    }


    fun isEmailPasswoedValid(passwoed: String): Boolean {
        return passwoed.length > 7
    }

    fun setupInformaionUser() {
        binding.apply {
            name = txtname.text.toString()
            email = txtEmail.text.toString()
            password = txtPassword.text.toString()

        }
    }

}

