package com.example.kovidtracker.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.example.kovidtracker.R

class QrFragment : Fragment() {

    lateinit var qr: TextView
    lateinit var btn_qr: ImageButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_qr, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        qr = view.findViewById(R.id.tv_qr)
        btn_qr = view.findViewById(R.id.btn_qr)

        qr.setOnClickListener{
            Toast.makeText(context, "This is QR Fragment", Toast.LENGTH_SHORT).show()
        }

        btn_qr.setOnClickListener {
            val newFragment = CheckInResultFragment()
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.fragment_container, newFragment)
            transaction?.commit()
        }
    }

}