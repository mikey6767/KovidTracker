package com.example.kovidtracker.fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.kovidtracker.R
import com.example.kovidtracker.fragments.EditProfileFragment.Companion.newInstance
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso


private const val USRNAME = "usrname"

class EditProfileFragment : Fragment() {

//    lateinit var profilename: EditText
//    lateinit var IC: EditText
//    lateinit var phonenumber: EditText
//    lateinit var email: EditText
//    lateinit var password: EditText
//    lateinit var confirmpassword: EditText
//    lateinit var fAuth: FirebaseAuth
//    lateinit var fStore: FirebaseFirestore
//    lateinit var user: FirebaseUser
//    lateinit var profileImage: ImageView
//    lateinit var storageReference: StorageReference
//    lateinit var userId: String
//
//    private var usrname: String? = null
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        arguments?.let {
//            usrname = it.getString(USRNAME)
//        }
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_edit_profile, container, false)
//    }
//
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        profilename = view.findViewById(R.id.et_profileName)
//        IC = view.findViewById(R.id.et_profileIC)
//        phonenumber = view.findViewById(R.id.et_profilephonenumber)
//        email = view.findViewById(R.id.et_profileemail)
//        password = view.findViewById(R.id.et_profilepassword)
//        confirmpassword = view.findViewById(R.id.et_profileconfirmpassword)
//        profileImage = view.findViewById(R.id.img_profilePictureChange)
//        fAuth = FirebaseAuth.getInstance()
//        fStore = FirebaseFirestore.getInstance()
//        storageReference = FirebaseStorage.getInstance().reference
//
//        val profileRef = storageReference.child("users/" + fAuth.currentUser!!.uid + "/profile.jpg")
//        profileRef.downloadUrl.addOnSuccessListener { uri ->
//            Picasso.get().load(uri).into(profileImage)
//        }
//
//        userId = fAuth.currentUser!!.uid
//        user = fAuth.currentUser!!
//
//        val documentReference = fStore.collection("users").document(userId)
//        documentReference.addSnapshotListener(
//            { documentSnapshot, e ->
//                if (documentSnapshot!!.exists()) {
//                    phonenumber.setText(documentSnapshot.getString("phone"))
//                    IC.setText(documentSnapshot.getString("IC"))
//                    profilename.setText(documentSnapshot.getString("name"))
//                    email.text = documentSnapshot.getString("email")
//                } else {
//                    Log.d("tag", "onEvent: Document do not exists")
//                }
//            })
//
//        view.findViewById<Button>(R.id.img_profilePictureChange).setOnClickListener {
//            fun onClick(v: View?) {
//                val openGalleryIntent =
//                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
//                startActivityForResult(openGalleryIntent, 1000)
//            }
//        }
//
//
//
//
//
//
//
//
//        view.findViewById<Button>(R.id.btn_updateprofile).setOnClickListener {
//            val cemail: String = et_profileemail.getText().toString().trim { it <= ' ' }
//            val cpassword: String = mPassword.getText().toString().trim { it <= ' ' }
//            val cfullName: String = mName.getText().toString()
//            val cphone: String = mPhone.getText().toString()
//            val cIC: String = mIC.getText().toString()
//
//
//            if (TextUtils.isEmpty(fullName)) {
//                mName.setError("Name is Required!")
//                return
//            }
//
//            if (TextUtils.isEmpty(phone)) {
//                mPhone.setError("Phone is Required!")
//                return
//            }
//
//            if (TextUtils.isEmpty(IC)) {
//                mIC.setError("IC is Required!")
//                return
//            }
//
//            if (TextUtils.isEmpty(email)) {
//                mEmail.setError("Email is Required!")
//                return
//            }
//
//            if (TextUtils.isEmpty(password)) {
//                mPassword.setError("Password is Required!")
//                return
//            }
//
//            if (password.length < 6) {
//                mPassword.setError("Password Must be More Than Characters")
//                return
//            }
//        }
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//        view.findViewById<Button>(R.id.btn_updateprofilepassword).setOnClickListener {
//            requireActivity().supportFragmentManager.beginTransaction()
//                .replace(R.id.fragment_container, EditProfileFragment.newInstance("AAA")).commit()
//        }
//
//        view.findViewById<Button>(R.id.btn_updateprofilepassword).setOnClickListener {
//            requireActivity().supportFragmentManager.beginTransaction()
//                .replace(R.id.fragment_container, ProfileFragment.newInstance("AAA")).commit()
//            activity?.finish()
//        }
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//    }
//
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment EditProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String) =
            EditProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(USRNAME, param1)
                }
            }
    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == 1000) {
//            if (resultCode == Activity.RESULT_OK) {
//                val imageUri = data!!.data
//
//                //profileImage.setImageURI(imageUri);
//                uploadImageToFirebase(imageUri)
//            }
//        }
//    }
//
//    private fun uploadImageToFirebase(imageUri: Uri) {
//        // uplaod image to firebase storage
//        val fileRef = storageReference.child("users/" + fAuth.currentUser!!.uid + "/profile.jpg")
//        fileRef.putFile(imageUri).addOnSuccessListener {
//            fileRef.downloadUrl.addOnSuccessListener { uri ->
//                Picasso.get().load(uri).into(img_profilePictureChange)
//            }
//        }.addOnFailureListener {
//            Toast.makeText(getApplicationContext(), "Failed.", Toast.LENGTH_SHORT).show()
//        }
//    }
}