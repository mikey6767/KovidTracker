package com.example.kovidtracker.fragments

import android.os.Bundle
import android.util.JsonReader
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.kovidtracker.R
import com.example.kovidtracker.ViewPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.firebase.firestore.FirebaseFirestore
import java.io.StringReader

class InfoFragment : Fragment() {

    lateinit var info: TextView
    lateinit var tabLayout: TabLayout
    lateinit var viewPager: ViewPager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tabLayout = view.findViewById(R.id.tabLayout)
        viewPager = view.findViewById(R.id.viewPager)

        FirebaseFirestore.getInstance().collection("common").document("info").get().addOnSuccessListener {
            viewPager.adapter = ViewPagerAdapter(childFragmentManager, it["know"].toString(), it["do"].toString())
            tabLayout.setupWithViewPager(viewPager)
        }

    }

    private fun buildData(json: String){

    }
}