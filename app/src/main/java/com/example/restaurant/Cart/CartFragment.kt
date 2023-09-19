package com.example.restaurant.Cart

import BaseFragment
import android.content.DialogInterface
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.restaurant.R

import com.example.restaurant.database.ShopImp
import com.example.restaurant.databinding.FragmenCartBinding
import com.example.restaurant.localData.SharedPrefs
import com.example.restaurant.search.model.Shop
import com.example.restaurant.showConfirmationDialog
import kotlinx.coroutines.launch


class CartFragment : BaseFragment<FragmenCartBinding>(), listenerCart {

    lateinit var shapImp: ShopImp
    lateinit var adpterCart: CartAdapter
    var id=""
    override val inflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmenCartBinding =
        FragmenCartBinding::inflate

    override fun setUp() {

        shapImp = ShopImp()
        lifecycleScope.launch {
            var listOfShop =
                shapImp.getAllOrderOfUserById(SharedPrefs.getUserIdFromPrefs(requireContext()))
            adpterCart = CartAdapter(listOfShop, this@CartFragment)
            binding.recyclerCart.adapter = adpterCart
        }


    }

    override fun addCallBacks() {
    }


    override fun onCliceLocation(shop: Shop) {
        findNavController().navigate(R.id.action_action_cart_to_mapsFragment)
    }

    override fun onClickDeleteShop(id: String) {
        this.id=id
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
                shapImp.deleteItemShopById(id)
                adpterCart.setData(shapImp.getAllOrderOfUserById(SharedPrefs.getUserIdFromPrefs(requireContext())))
            }
            }


    private val negativeButtonListener =
        DialogInterface.OnClickListener { dialog, which ->

        }

}