package com.example.kovidtracker.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.kovidtracker.EditprofileActivity
import com.example.kovidtracker.LoginActivity
import com.example.kovidtracker.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso

class ProfileFragment : Fragment() {

    lateinit var profilename: TextView
    lateinit var IC: TextView
    lateinit var phonenumber: TextView
    lateinit var email: TextView
    lateinit var fAuth: FirebaseAuth
    lateinit var fStore: FirebaseFirestore
    lateinit var user: FirebaseUser
    lateinit var profileImage: ImageView
    lateinit var storageReference: StorageReference
    lateinit var userId: String



    private var usrname = ""


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        profilename = view.findViewById(R.id.tv_profileName)
        profileImage = view.findViewById(R.id.img_profilePicture)
        IC= view.findViewById(R.id.tv_profileIC)
        phonenumber = view.findViewById(R.id.tv_profilePhoneNumber)
        email = view.findViewById(R.id.tv_profileEmail)

        fAuth = FirebaseAuth.getInstance()
        fStore = FirebaseFirestore.getInstance()
        storageReference = FirebaseStorage.getInstance().reference

        val profileRef = storageReference.child("users/" + fAuth.currentUser!!.uid + "/profile.jpg")
        profileRef.downloadUrl.addOnSuccessListener { uri ->
            Picasso.get().load(uri).into(profileImage)
        }

        userId = fAuth.currentUser!!.uid
        user = fAuth.currentUser!!

        val documentReference = fStore.collection("users").document(userId)
        documentReference.addSnapshotListener { documentSnapshot, e ->
            if (documentSnapshot!!.exists()) {
                phonenumber.setText(documentSnapshot.getString("phone"))
                IC.setText(documentSnapshot.getString("IC"))
                profilename.setText(documentSnapshot.getString("name"))
                email.text = documentSnapshot.getString("email")
            } else {
                Log.d("tag", "onEvent: Document do not exists")
            }
        }

        view.findViewById<Button>(R.id.btn_editprofile).setOnClickListener {

            val intent = Intent(requireContext(), EditprofileActivity::class.java)
            startActivity(intent)
            intent.putExtra("name", profilename.getText().toString())
            intent.putExtra("email", email.text.toString())
            intent.putExtra("phone", phonenumber.getText().toString())
            intent.putExtra("IC", IC.text.toString())
        }


        view.findViewById<Button>(R.id.profile_hs_btn).setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, HealthStatusFragment.newInstance("AAA")).commit()
        }
        view.findViewById<Button>(R.id.profile_faq).setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, FaqFragment.newInstance("AAA")).commit()
        }

        view.findViewById<Button>(R.id.profile_logout).setOnClickListener{

            FirebaseAuth.getInstance().signOut()
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            activity?.finish()
        }

    }

}