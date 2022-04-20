package com.example.kovidtracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.kovidtracker.fragments.*
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private val homeFragment = HomeFragment()
    private val infoFragment = InfoFragment()
    private val mapFragment = MapFragment()
    private val profileFragment = ProfileFragment()
    private val qrFragment = QrFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        currentFragment(homeFragment)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        val qrFAB = findViewById<FloatingActionButton>(R.id.fab_qr)

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.ic_home -> {
                    currentFragment(homeFragment)
                }
                R.id.ic_map -> {
                    currentFragment(mapFragment)
                }
                R.id.ic_qr -> {
                    currentFragment(qrFragment)
                }
                R.id.ic_info -> {
                    currentFragment(infoFragment)
                }
                R.id.ic_profile -> {
                    currentFragment(profileFragment)
                }
            }
            true
        }

        qrFAB.setOnClickListener {
            currentFragment(qrFragment)
        }

    }

    private fun currentFragment(fragment: Fragment){
        if(fragment != null){
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.commit()
        }
    }

}