package com.example.kovidtracker.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.example.kovidtracker.R


class HomeFragment : Fragment() {

    lateinit var home: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        home = view.findViewById<TextView>(R.id.tv_home)

        home.setOnClickListener{
            Toast.makeText(context, "This is Home Fragment", Toast.LENGTH_SHORT).show()
        }
    }

}