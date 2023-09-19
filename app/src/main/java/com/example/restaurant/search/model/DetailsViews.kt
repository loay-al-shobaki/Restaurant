package com.example.restaurant.search.model

import com.example.restaurant.util.RecipeViewType


data class DetailsViews<T>(val item: T, val type: RecipeViewType)
