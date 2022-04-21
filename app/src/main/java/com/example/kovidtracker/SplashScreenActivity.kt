package com.example.kovidtracker

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

class SplashScreenActivity : AppCompatActivity() {
    var loading_time = 4000

    //4000 = 4sec
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        Handler().postDelayed({ //Intent = navigation or transition from one screen to another.
            val home = Intent(this@LoginActivity, Login::class.java)
            startActivity(home)
            finish()
        }, loading_time.toLong())
    }
}