package com.example.restaurant

import BaseActivity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import com.example.restaurant.databinding.ActivityMainBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : BaseActivity<ActivityMainBinding>() {
    override val LOG_TAG: String = "loay"

    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding =
        ActivityMainBinding::inflate


    override fun setUp() {
        Log.d(LOG_TAG, "addCallbacks: ")
    }

    override fun addCallbacks() {
        Log.d(LOG_TAG, "addCallbacks: ")
        GlobalScope.launch {

        }
    }


}