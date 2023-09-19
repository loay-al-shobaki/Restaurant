import com.example.restaurant.database.IRecipeData

import android.util.Log
import com.example.restaurant.search.model.Recipe
import com.example.restaurant.toMap
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import com.google.firebase.firestore.Query
import kotlin.random.Random

class RecipeImp : IRecipeData {


    companion object {
        const val COLLECTION_RECIPE = "recipe"
        const val NAME_FIELD = "name"
        const val INGREDIENTS_FIELD = "ingredients"
        const val TOTAL_TIME_FIELD = "totalTimeInMinutes"
        const val CUISINE_FIELD = "cuisine"
        const val INSTRUCTION_FIELD = "instruction"
        const val SOURCE_URL_FIELD = "sourceUrl"
        const val CLEANED_INGREDIENTS_FIELD = "cleanedIngredients"
        const val IMAGE_URL_FIELD = "imageUrl"
        const val RATING_FIELD = "rating"
        const val PRICE_FIELD = "price"
    }

    private val db = Firebase.firestore
    private val collection_recipe = db.collection(COLLECTION_RECIPE)


    override fun addRecioeToDataBase(recipe: Recipe) {
        val recipeMap = recipe.toMap()
        collection_recipe.add(recipeMap)
    }

    override suspend fun getRecipeListDatabase(): List<Recipe> {
        val recipeList = mutableListOf<Recipe>()
        return try {
            val querySnapshot = collection_recipe.get().await()
            for (document in querySnapshot.documents) {
                val recipe = document.toObject(Recipe::class.java)
                recipe?.let {
                    Log.d("loay", "getRecipeListDatabase: $recipe")
                    recipeList.add(it)
                }
            }
            recipeList.shuffle(Random.Default)
            recipeList
        } catch (exception: Exception) {
            Log.e("Firestore", "Error retrieving recipes", exception)
            emptyList()
        }
    }

    override suspend fun updateDataRecipe(recipe: Recipe): Boolean {
        collection_recipe.document(recipe.id)
            .update(recipe.toMap()).await()
        return true

    }

    override suspend fun deleteDataRecipe(id: String): Boolean {
        var falg = false
        collection_recipe.document(id).delete().addOnSuccessListener {
            falg = true
        }.await()
        return falg
    }

    override suspend fun getRecipesWithRatingGreaterThanFour(): List<Recipe> {
        val recipes = mutableListOf<Recipe>()
        try {
            val querySnapshot = collection_recipe
                .whereGreaterThanOrEqualTo(RATING_FIELD, 4).orderBy(RATING_FIELD,Query.Direction.DESCENDING)
                .get()
                .await()

            for (document in querySnapshot.documents) {
                val recipe = document.toObject(Recipe::class.java)
                if (recipe != null) {
                    recipes.add(recipe)
                }
            }
        } catch (e: Exception) {

            e.printStackTrace()
        }

        return recipes
    }

     suspend fun getRecipeByName(urlimg: String): Recipe? {
        return try {
            val querySnapshot = collection_recipe.whereEqualTo(IMAGE_URL_FIELD, urlimg).get().await()
            val document = querySnapshot.documents.firstOrNull()
            val recipe = document?.toObject(Recipe::class.java)
            recipe?.let {
                Log.d("loay", "getRecipeByName: $recipe")
                it
            }
        } catch (exception: Exception) {
            Log.e("Firestore", "Error retrieving recipe by name", exception)
            null
        }
    }
}