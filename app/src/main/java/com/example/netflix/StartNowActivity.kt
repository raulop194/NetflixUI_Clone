package com.example.netflix

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.netflix.database.UsersHelper
import com.example.netflix.databinding.ActivityStartNowBinding
import com.example.netflix.utils.AuthUtils

class StartNowActivity : AppCompatActivity() {
    lateinit var binding: ActivityStartNowBinding
    lateinit var usersHelper: UsersHelper

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartNowBinding.inflate(layoutInflater)
        setContentView(binding.root)


        usersHelper = UsersHelper(applicationContext) // Conexión con la base de datos de usuarios.

        /* ******************** */
        /* Sección de listeners */
        /* ******************** */

        binding.closeButton.setOnClickListener { finish() } // Boton que Finaliza esta actividad

        binding.registerStartNowButton.setOnClickListener {
            val emailEditText = binding.registerEmail // "EditText" con id "register_email".
            val email = emailEditText.text.toString() // Texto del componente.
            val warmText = binding.warmText // "Texview donde los errores se reflejan".

            /*
            * Avisa en caso de que el campo "register_email" este vacio o en blanco.
            * */
            if (email.isEmpty() || email.isBlank()) {
                emailEditText.setBackgroundResource(R.drawable.edit_text_error)
                warmText.text = "El email es obligatorio."
                return@setOnClickListener
            }

            /*
            * Avisa en caso de que el email no cumpla con el minimo de 5 caracteres y maximo de
            * 50 caracteres.
            * */
            if (!AuthUtils.hasLength(email)) {
                emailEditText.setBackgroundResource(R.drawable.edit_text_error)
                warmText.text = "El email debe de tener entre 5 y 50 caracteres."
                return@setOnClickListener
            }

            /*
            * Avisa en caso de que el email no tenga el formato correcto que debe de tener un
            * email.
            * */
            if (!AuthUtils.isValidEmail(email)) {
                emailEditText.setBackgroundResource(R.drawable.edit_text_error)
                warmText.text = "Ingresa una dirección de email válida."
                return@setOnClickListener
            }

            /*
            * En caso de que el usuario con "email" exista te redirigira a la actividad
            * "LoginActivity" si no a "RegisterACtivity".
            * */
            val nextActivity =
                    if (usersHelper.checkUserByEmail(email) != null) LoginActivity::class.java
                    else RegisterActivity::class.java

            /*
            * Encola el inicio de una nueva actividad a la pila de Actividades. Manda como dato
            * extra el email introducido en el campo email.
            * */
            startActivity(
                Intent(this, nextActivity)
                    .putExtra("EMAIL_INTRODUCED", email)
            )
            finish()
        }

    }




}