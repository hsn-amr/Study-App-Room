package com.example.studyapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    lateinit var btnKotlinReview: Button
    lateinit var btnAndroidReview: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnAndroidReview = findViewById(R.id.btnAndroidReview)
        btnKotlinReview = findViewById(R.id.btnKotlinReview)

        btnKotlinReview.setOnClickListener {
            val kotlinIntent = Intent(this, KotlinReview::class.java)
            startActivity(kotlinIntent)
        }

        btnAndroidReview.setOnClickListener {
            val androidIntent = Intent(this, AndroidReview::class.java)
            startActivity(androidIntent)
        }
    }
}