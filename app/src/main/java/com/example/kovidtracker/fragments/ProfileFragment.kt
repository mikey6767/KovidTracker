package com.example.kovidtracker.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.kovidtracker.LoginActivity
import com.example.kovidtracker.R
import com.google.firebase.auth.FirebaseAuth

class ProfileFragment : Fragment() {

    private var usrname = ""
    //lateinit var profile: TextView
    //private val buttonsFragMap = mapOf(R.id.profile_his_btn to HistoryFragment.newInstance(usrname), R.id.profile_hs_btn to HealthStatusFragment.newInstance(usrname), R.id.profile_faq to FaqFragment.newInstance(usrname))

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.profile_editbtn).setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, EditProfileFragment.newInstance("AAA")).commit()
        }

        view.findViewById<Button>(R.id.profile_his_btn).setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, HistoryFragment.newInstance("AAA")).commit()
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
            finish()
        }
        //profile = view.findViewById<TextView>(R.id.tv_profile)

        /*profile.setOnClickListener{
            Toast.makeText(context, "This is Profile Fragment", Toast.LENGTH_SHORT).show()
        }*/
        //requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragment_container, HistoryFragment.newInstance("AAA")).commit()
        //requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragment_container, HealthStatusFragment.newInstance("AAA")).commit()
    }

    /*override fun onClick(btn: View?) {
        requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragment_container, buttonsFragMap[btn?.id]!!)
    }*/
}