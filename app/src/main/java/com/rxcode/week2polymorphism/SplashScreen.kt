package com.rxcode.week2polymorphism

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.rxcode.week2polymorphism.databinding.ActivitySplashScreenBinding

class SplashScreen : AppCompatActivity() {

    private lateinit var viewBind: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBind = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(viewBind.root)

        //show list item
        showList()

    }

    private fun showList(){
        Handler(Looper.getMainLooper()).postDelayed({
            val die = Intent(this, MainActivity::class.java)
            die.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(die)
        }, 3000)
    }
}