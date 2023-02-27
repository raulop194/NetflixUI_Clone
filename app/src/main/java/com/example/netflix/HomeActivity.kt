package com.example.netflix

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.netflix.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.profileImg.setImageResource(
            intent.getIntExtra("PROFILE_RES", R.mipmap.profile_1)
        )


        var isConnected = false
        binding.connectButton.setOnClickListener {
            val image = it as ImageView

            if (isConnected) image.setImageResource(R.mipmap.cast)
            else image.setImageResource(R.mipmap.cast_connected)

            isConnected = !isConnected
        }

        binding.profileImg.setOnClickListener {
            startActivity(
                Intent(this, ProfilesActivity::class.java)
            )
            finish()
        }

    }
}