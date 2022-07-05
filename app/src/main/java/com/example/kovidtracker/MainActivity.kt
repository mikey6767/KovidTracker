package com.example.kovidtracker

import android.content.Intent
import android.os.Bundle
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.kovidtracker.fragments.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity() {

    private val homeFragment = HomeFragment()
    private val infoFragment = InfoFragment()
    private val statusFragment = StatusFragment()
    private val profileFragment = ProfileFragment()
    private val checkInFragment = CheckInFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        currentFragment(homeFragment)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        val qrFAB = findViewById<FloatingActionButton>(R.id.fab_qr)

        //Fixme Got Error. my idea was use the OnMenuItemClickListener to navigate But
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.ic_home -> {
                    currentFragment(homeFragment)
                }
                R.id.ic_status -> {
                    currentFragment(statusFragment)
                }
                R.id.ic_qr -> {
                    currentFragment(checkInFragment)
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
            currentFragment(checkInFragment)
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

fun onActivityResult(requestCode: Int, resultCode: Int, @Nullable data: Intent?) {
    onActivityResult(requestCode, resultCode, data)
}