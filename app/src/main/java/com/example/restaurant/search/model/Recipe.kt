package com.example.restaurant.search.model


import android.media.Rating
import android.os.Parcelable
import com.google.firebase.firestore.DocumentId
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Recipe(
    @DocumentId
    var id: String,
    var name: String,
    var ingredients: List<String>,
    var totalTimeInMinutes: Int,
    var cuisine: String,
    var instruction: List<String>,
    var sourceUrl: String,
    var cleanedIngredients: List<String>,
    var imageUrl: String,
    var rating: Float,
    var price: Float,

):Parcelable {
    constructor() : this("", "", listOf(), 0, "", listOf(), "",listOf(),"",0f,0f)

}