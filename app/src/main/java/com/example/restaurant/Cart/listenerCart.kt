package com.example.restaurant.Cart

import com.example.restaurant.search.model.Shop

interface  listenerCart {
    fun onClickDeleteShop(id:String)
    fun onCliceLocation(shop: Shop)
}