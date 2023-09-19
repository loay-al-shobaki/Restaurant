package com.example.restaurant.setting

import BaseFragment
import android.content.DialogInterface
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.restaurant.R
import com.example.restaurant.database.UserImp
import com.example.restaurant.databinding.FragmentSettingBinding
import com.example.restaurant.localData.SharedPrefs
import com.example.restaurant.localData.SharedPrefs.getUserIdFromPrefs
import com.example.restaurant.search.model.Users
import com.example.restaurant.showConfirmationDialog
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class SettingFragment : BaseFragment<FragmentSettingBinding>() {

    private val PICK_IMAGE_REQUEST = 1
    private lateinit var editTextDate: TextInputEditText
    private lateinit var dateFormatter: SimpleDateFormat
    val userData = UserImp()
//    val storage = Firebase.storage
//    val storageRef = storage.reference
//    val imgeRef = storageRef.child("imagesPofile")
//    val childRef = imgeRef.child(System.currentTimeMillis().toString() + "loay.png")

//    var downloadUrl = "null"
//    private val contract = registerForActivityResult(ActivityResultContracts.GetContent()) {
//        binding.imageprofile.setImageURI(it)
//    }

    override val inflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSettingBinding =
        FragmentSettingBinding::inflate

    override fun setUp() {


        editTextDate = binding.editTextDate
        dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.US)
        editTextDate.setOnClickListener {
            showDatePicker()
        }

        lifecycleScope.launch {
            val userdata = userData.getDataUserDatabaseById(getUserIdFromPrefs(requireContext()))
            getdataUserAndSendDatatoScreen(userdata)
        }


    }

    override fun addCallBacks() {
        binding.btnsubmit.setOnClickListener {
            lifecycleScope.launch {
                if (userData.updateUserdata(
                        getUserIdFromPrefs(requireContext()),
                        changedata(),
                        binding.txtConfirmpassword.text.toString()
                    )
                ) {
                    Toast.makeText(context, "User data updated successfully!", Toast.LENGTH_SHORT)
                        .show()
                } else Toast.makeText(context, "Error updating user data!", Toast.LENGTH_SHORT)
                    .show()
            }

//            val bitmap = (binding.imageprofile.drawable as BitmapDrawable).bitmap
//            val baos = ByteArrayOutputStream()
//            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
//            val data = baos.toByteArray()
//
//            val uploadTask = childRef.putBytes(data)
//            uploadTask.addOnFailureListener {
//                // Handle unsuccessful uploads
//            }.addOnSuccessListener { taskSnapshot ->
//                childRef.downloadUrl.addOnSuccessListener { uri ->
//                    downloadUrl = uri.toString()
//                    Log.d("loay", "addCallBacks: $downloadUrl")
//                     SharedPrefs.saveUseurlImgProfile(requireContext(),downloadUrl)
//                }.addOnFailureListener {
//                    // Handle failure to retrieve the download URL
//                }
//            }


        }

        binding.txtsignout.setOnClickListener {
            SharedPrefs.clearTextInSharedPreferences(requireContext())
            onClickDeleteRecipe()
        }
        binding.editTextLocation.setOnClickListener {
            findNavController().navigate(R.id.action_action_setting_to_mapUserFragment2)
        }
//        binding.txtChangeprofile.setOnClickListener {
//            contract.launch("image/*")
//        }


    }

    fun changedata(): Users {
        return Users(
            "",
            binding.txtnameold.text.toString(),
            binding.txtEmailold.text.toString(),
            binding.txtpasswordold.text.toString(),
            binding.editTextDate.text.toString(),
            binding.editTextLocation.text.toString(),

            )
    }

    fun getdataUserAndSendDatatoScreen(user: Users) {
        binding.txtnameold.setText(user.name)
        binding.txtEmailold.setText(user.email)
        binding.txtpasswordold.setText(user.password)
        binding.editTextDate.setText(user.dateOfBirth)
        binding.editTextLocation.setText(user.location)
    }


    fun onClickDeleteRecipe() {
        context?.showConfirmationDialog(
            "Confirmation",
            "Are you sure you want to sign out?",
            positiveButtonListener,
            negativeButtonListener
        )


    }


    private val positiveButtonListener =
        DialogInterface.OnClickListener { dialog, which ->
            SharedPrefs.clearTextInSharedPreferences(requireContext())
            Log.d("loay", "${SharedPrefs.isTheUserIdIsFound(requireContext())} ")
            findNavController().navigate(R.id.action_action_setting_to_mainActivity)

        }

    private val negativeButtonListener =
        DialogInterface.OnClickListener { dialog, which ->

        }

    private fun showDatePicker() {
        val calendarConstraintsBuilder = CalendarConstraints.Builder()
        val datePickerBuilder = MaterialDatePicker.Builder.datePicker()
        datePickerBuilder.setTitleText("Select a date")
        datePickerBuilder.setCalendarConstraints(calendarConstraintsBuilder.build())
        val materialDatePicker = datePickerBuilder.build()
        materialDatePicker.addOnPositiveButtonClickListener { selectedDate ->
            val formattedDate: String = dateFormatter.format(Date(selectedDate))
            binding.editTextDate.setText(formattedDate)
        }
        materialDatePicker.show(requireActivity().supportFragmentManager, "DATE_PICKER")
    }


}