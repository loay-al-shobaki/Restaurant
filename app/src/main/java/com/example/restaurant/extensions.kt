package com.example.restaurant


import com.example.restaurant.search.model.Recipe
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import com.example.restaurant.database.ShopImp
import com.example.restaurant.search.model.DetailsViews
import com.example.restaurant.search.model.Shop
import com.example.restaurant.util.RecipeViewType

fun String.toList(): List<String> {
    return this.split(";")
}

fun Recipe.toMap(): Map<String, Any?> {
    return mapOf(
        RecipeImp.NAME_FIELD to name,
        RecipeImp.INGREDIENTS_FIELD to ingredients,
        RecipeImp.TOTAL_TIME_FIELD to totalTimeInMinutes,
        RecipeImp.CUISINE_FIELD to cuisine,
        RecipeImp.INSTRUCTION_FIELD to instruction,
        RecipeImp.SOURCE_URL_FIELD to sourceUrl,
        RecipeImp.CLEANED_INGREDIENTS_FIELD to cleanedIngredients,
        RecipeImp.IMAGE_URL_FIELD to imageUrl,
        RecipeImp.RATING_FIELD to rating,
        RecipeImp.PRICE_FIELD to price
    )
}


fun Shop.toMap(): Map<String, Any?> {
    return mapOf(
        ShopImp.FIELD_ID_USER to IdUser,
        ShopImp.FIELD_PRICE to price,
        ShopImp.FIELD_COUNT_OF_RECPE to countOfRecipe,
        ShopImp.FIELD_URL_RECPE to UrlOfRecipe,
        ShopImp.FIELD_RECIPE_NAME to recipeName,

    )
}

fun List<String>.convertToStringWithSemicolon(): String {
    return this.joinToString(separator = ";")
}



fun Context.showConfirmationDialog(
    title: String,
    message: String,
    positiveButtonListener: DialogInterface.OnClickListener,
    negativeButtonListener: DialogInterface.OnClickListener
) {
    val builder = AlertDialog.Builder(this)
    builder.setTitle(title)
    builder.setMessage(message)
    builder.setPositiveButton("Yes", positiveButtonListener)
    builder.setNegativeButton("No", negativeButtonListener)
    builder.show()
}

fun String.toDetailsItem(): DetailsViews<Any> {
    return DetailsViews(this, RecipeViewType.ITEM_INGREDIENT)
}
