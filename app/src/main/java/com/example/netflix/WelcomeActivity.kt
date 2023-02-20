package com.example.netflix

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.netflix.databinding.ActivityWelcomeBinding

class WelcomeActivity: AppCompatActivity() {
    lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.startNowButton.setOnClickListener {
            startActivity(Intent(this, StartNowActivity::class.java))
        }

    }
}