package com.example.restaurant.admin

import BaseFragment
import RecipeImp
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.util.Log

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.Navigation.findNavController
import com.example.restaurant.R

import com.example.restaurant.databinding.FragmentAdminBinding
import com.example.restaurant.localData.SharedPrefs
import com.example.restaurant.search.model.Recipe
import com.example.restaurant.toList
import com.google.firebase.firestore.core.View
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.ByteArrayOutputStream


class EnterDataAdminFragment : BaseFragment<FragmentAdminBinding>() {
    val recipeData = RecipeImp()
    lateinit var recipe_obj: Recipe
    val storage = Firebase.storage
    val storageRef = storage.reference
    val imgeRef = storageRef.child("imagesRecipe")
    val childRef = imgeRef.child(System.currentTimeMillis().toString() + "loay.png")

    var downloadUrl = "null"



    override val inflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentAdminBinding =
        FragmentAdminBinding::inflate

    override fun setUp() {


         val contract = registerForActivityResult(ActivityResultContracts.GetContent()) {
            binding.imgRecipe.visibility=android.view.View.VISIBLE
             binding.imgRecipe.setImageURI(it)
             val bitmap = (binding.imgRecipe.drawable as BitmapDrawable).bitmap
             val baos = ByteArrayOutputStream()
             bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
             val data = baos.toByteArray()

             val uploadTask = childRef.putBytes(data)
             uploadTask.addOnFailureListener {
                 // Handle unsuccessful uploads
             }.addOnSuccessListener { taskSnapshot ->
                 childRef.downloadUrl.addOnSuccessListener { uri ->
                     downloadUrl = uri.toString()
                     Log.d("loay", "addCallBacks: $downloadUrl")
                     binding.editTxtImgUrl.setText(downloadUrl)
                 }.addOnFailureListener {
                     // Handle failure to retrieve the download URL
                 }
             }
        }
        binding.editTxtImgUrl.setOnClickListener {
            contract.launch("image/*")

        }




    }

    override fun addCallBacks() {
        binding.run {
            btnAddToDatabase.setOnClickListener {
                recipe_obj = Recipe("",
                    editTxtName.text.toString(),
                    editTxtIngredients.text.toString().toList(),
                    editTxtTime.text.toString().toInt(),
                    editTxtCuisine.text.toString(),
                    editTxtInstruction.text.toString().toList(),
                    editTxtSourceUrl.text.toString(),
                    editTxtCleanIngredients.text.toString().toList(),
                    downloadUrl.toString(),
                    ratingBar.rating,
                    editTxtPrice.text.toString().toFloat()

                )
                addRecipetoDataBase(recipe_obj)


            }

            btnUpDateRecipeToDatabase.setOnClickListener {
                findNavController(it).navigate(R.id.action_adminFragment_to_showRecipeAdminFragment)
            }

        }
    }


    fun addRecipetoDataBase(recipe: Recipe) {
        recipeData.addRecioeToDataBase(recipe)
    }







}