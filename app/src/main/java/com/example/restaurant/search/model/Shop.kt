package com.example.restaurant.search.model

import android.os.Parcelable
import com.google.firebase.firestore.DocumentId
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Shop (
    @DocumentId
    val id:String,
    val IdUser: String,
    val recipeName: String,
    val UrlOfRecipe: String,
    val price: Float,
    val countOfRecipe: Int
):Parcelable{
    constructor():this("","","","",0f,3)
}