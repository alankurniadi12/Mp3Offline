package com.mp3.offline.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.mp3.offline.R

class SplashScreen : AppCompatActivity() {

    private val SPLASH_TIME: Long = 2500

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash__screen)

        Handler().postDelayed({
            startActivity(Intent (this, MainActivity::class.java))
            finish()
        }, SPLASH_TIME)
    }
}