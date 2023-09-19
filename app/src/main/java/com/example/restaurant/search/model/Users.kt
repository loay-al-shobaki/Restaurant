package com.example.restaurant.search.model

import com.google.firebase.firestore.DocumentId

data class Users(
    @DocumentId
    var id: String,
    var name: String,
    var email: String,
    var password: String,
    var dateOfBirth: String,
    var location: String,
  //  var urlImgProfile:String
) {
    constructor() : this("", "", "","","","")

}