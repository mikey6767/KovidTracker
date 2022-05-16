package com.example.kovidtracker.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kovidtracker.R

class ProfileFragment : Fragment() {

    //lateinit var profile: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //profile = view.findViewById<TextView>(R.id.tv_profile)

        /*profile.setOnClickListener{
            Toast.makeText(context, "This is Profile Fragment", Toast.LENGTH_SHORT).show()
        }*/
        //requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragment_container, HistoryFragment.newInstance("AAA")).commit()
        requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragment_container, HealthStatusFragment.newInstance("AAA")).commit()
    }
}