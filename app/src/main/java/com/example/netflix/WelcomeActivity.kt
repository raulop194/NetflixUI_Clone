package com.example.netflix

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.netflix.databinding.ActivityWelcomeBinding

class WelcomeActivity: AppCompatActivity() {
    lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /* ******************** */
        /* Sección de listeners */
        /* ******************** */

        binding.startNowButton.setOnClickListener {
            startActivity(Intent(this, StartNowActivity::class.java))
        }
        binding.welcomeLoginButton.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
        binding.welcomePrivacyButton.setOnClickListener {
            val helpUri = "https://help.netflix.com/legal/privacy" // Netflix privacy

            /*
            * Encola un nuevo inicio de una actividad de tipo "NEW_TASK" para el
            * paquete (o aplicación) "com.android.chrome".
            * */
            startActivity(
                Intent(Intent.ACTION_VIEW, Uri.parse(helpUri))
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    .setPackage("com.android.chrome")
            )
        }

    }
}