package com.example.restaurant.database

import android.util.Log
import com.example.restaurant.search.model.Recipe
import com.example.restaurant.search.model.Users
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import java.text.FieldPosition

class UserImp : IUserData {

    companion object {
        private const val COLLECTION_USER = "users"
        private const val FIELD_NAME = "name"
        private const val FIELD_EMAIL = "email"
        private const val FIELD_PASSWORD="password"
        private const val FIELD_DATEOFBIRTH="dateOfBirth"
        private const val FIELD_LOCATION="location"
        private const val FIELD_URLIMGProfile="urlImgProfile"
    }

    val db = Firebase.firestore
    val collection = db.collection(COLLECTION_USER)


    override fun addUserToDataBase(user: Users) {
        collection
            .add(user)
            .addOnSuccessListener {
                Log.d("loay", "User added successfully!")
            }
            .addOnFailureListener { e ->
                Log.w("loay", "Error adding user", e)
            }

    }

    override suspend fun getIdIfUersIfExist(email: String, password: String  ):String {
        var id =""
        collection.get().addOnSuccessListener {
            for (document in it) {
                if (document.data[FIELD_EMAIL] == email && document.data[FIELD_PASSWORD] == password){
                   id =document.id

                }
            }
        }.await()
        return  id
    }


    override suspend fun updateUserdata(oldId:String, user: Users, passwordConform:String): Boolean {
        var flag= false
        val updates = mapOf(
            FIELD_NAME to user.name,
            FIELD_EMAIL to user.email,
            FIELD_PASSWORD to user.password,
            FIELD_DATEOFBIRTH to user.dateOfBirth,
            FIELD_LOCATION to user.location,

        )
        if (user.password==passwordConform){
            collection.document(oldId).update(updates)   .addOnSuccessListener {
                flag = true
            }.await()

        }

        return true
    }

    override suspend fun getDataUserDatabaseById(id: String): Users {
        var user = Users()
        try {
            val querySnapshot = collection.document(id).get().await()
            val userData = querySnapshot.toObject(Users::class.java)
            userData?.let {
                Log.d("loay", "getUserDatabaseById: $userData")
                user = userData
            }
        } catch (exception: Exception) {
            Log.e("Firestore", "Error retrieving user", exception)
        }
        return user
    }

    suspend fun addUserLocationToFirestoreById(id: String, location: String) {
        val user = hashMapOf(
            "location" to location
        )
        try {
            val documentRef = collection.document(id)
            documentRef.set(user, SetOptions.merge()).await()
            Log.d("loay", "User name added successfully")
        } catch (exception: Exception) {
            Log.e("Firestore", "Error adding user name", exception)
        }
    }

}
