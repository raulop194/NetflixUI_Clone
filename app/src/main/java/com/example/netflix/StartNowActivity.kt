package com.example.netflix

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.netflix.databinding.ActivityStartNowBinding

class StartNowActivity : AppCompatActivity() {
    lateinit var binding: ActivityStartNowBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartNowBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.closeButton.setOnClickListener { finish() }
        binding.registerStartNowButton.setOnClickListener {
            val emailEditText = binding.registerEmail
            val email = binding.registerEmail.text.toString()
            val warmText = binding.warmText

            if (email.isEmpty() || email.isBlank()) {
                emailEditText.setBackgroundResource(R.drawable.edit_text_error)
                warmText.text = "El email es obligatorio."
                return@setOnClickListener
            }

            if (!hasLength(email)) {
                emailEditText.setBackgroundResource(R.drawable.edit_text_error)
                warmText.text = "El email debe de tener entre 5 y 50 caracteres."
                return@setOnClickListener
            }

            if (!isValidEmail(email)) {
                emailEditText.setBackgroundResource(R.drawable.edit_text_error)
                warmText.text = "Ingresa una dirección de email válida."
                return@setOnClickListener
            }

            startActivity(
                Intent(this, LoginActivity::class.java)
                    .putExtra("EMAIL_INTRODUCED", warmText.text)
            )
        }

    }

    fun hasLength(email: String): Boolean {
        return email.length in 5..15
    }

    fun isValidEmail(email: String): Boolean{
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}