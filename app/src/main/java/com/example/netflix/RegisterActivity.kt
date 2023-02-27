package com.example.netflix

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.netflix.database.UsersHelper
import com.example.netflix.databinding.ActivityRegisterBinding
import com.example.netflix.utils.AuthUtils

class RegisterActivity : AppCompatActivity() {
    lateinit var binding: ActivityRegisterBinding
    lateinit var usersHelper: UsersHelper

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        usersHelper = UsersHelper(applicationContext) // Conexión con la base de datos de usuarios.

        /*
        * Establece el email introducido en la anterior actividad en el campo de texto con id
        * "register_email2" como valor por defecto
        * */
        binding.registerEmail2.setText(intent.getStringExtra("EMAIL_INTRODUCED")?:"")

        /* ******************** */
        /* Sección de listeners */
        /* ******************** */

        binding.helpButton.setOnClickListener {
            val helpUri = "https://help.netflix.com/es-es" // Netflix help webpage

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

        binding.loginButton.setOnClickListener { finishGoTo(LoginActivity::class.java) }

        binding.registerButton.setOnClickListener {
            val emailField = binding.registerEmail2
            val passwordField = binding.registerPassword

            val emailWarm = binding.registerEmail2Warm
            val passwordWarm = binding.registerPaswordWarm

            val email = emailField.text.toString()
            val password = passwordField.text.toString()

            var isWrong = false

            /* ************ */
            /* Email Policy */
            /* ************ */

            /*
            * Avisa en caso de que el campo "register_email2" este vacio o en blanco.
            * */
            if (email.isEmpty() || email.isBlank()) {
                emailField.setBackgroundResource(R.drawable.edit_text_error)
                emailWarm.text = "El email es obligatorio."
                isWrong = true
            }

            /*
            * Avisa en caso de que el email no cumpla con el minimo de 5 caracteres y maximo de
            * 50 caracteres.
            * */
            else if (!AuthUtils.hasLength(email)) {
                emailField.setBackgroundResource(R.drawable.edit_text_error)
                emailWarm.text = "El email debe de tener entre 5 y 50 caracteres."
                isWrong = true
            }

            /*
            * Avisa en caso de que el email no tenga el formato correcto que debe de tener un
            * email.
            * */
            else if (!AuthUtils.isValidEmail(email)) {
                emailField.setBackgroundResource(R.drawable.edit_text_error)
                emailWarm.text = "Ingresa una dirección de email válida."
                isWrong = true
            }
            else {
                emailField.setBackgroundResource(R.drawable.edit_text_correct)
                emailWarm.text = ""
            }

            /* *************** */
            /* Password policy */
            /* *************** */

            /*
            * Avisa en caso de que el campo "register_password" este vacio o en blanco.
            * */
            if (password.isEmpty() || password.isBlank()) {
                passwordField.setBackgroundResource(R.drawable.edit_text_error)
                passwordWarm.text = "Contraseña obligatoria."
                isWrong = true
            }
            /*
            * Avisa en caso de que la contraseña no cumpla con los requisitos.
            * */
            else if (!AuthUtils.isValidPassword(password)) {
                passwordField.setBackgroundResource(R.drawable.edit_text_error)
                passwordWarm.text = "La contraseña debe contener estos requisitos:\n" +
                        "Entre 6-60 caracteres\n" +
                        "Mínimo una letra y un número"
                isWrong = true
            }
            else {
                passwordField.setBackgroundResource(R.drawable.edit_text_correct)
                passwordWarm.text = ""
            }

            /*
            * Si existe un error en la validación, no continuara con el proceso
            * */
            if (isWrong) return@setOnClickListener

            usersHelper.addUser(email, password) // Registra un nuevo usuario en la tabla
            finishGoTo(LoginActivity::class.java)
        }

    }



    /**
     * Encola el inicio de una actividad, y finaliza esta actividad.
     *
     * @param activityClass Clase de la nueva activiadad.
     * */
    private fun finishGoTo(activityClass: Class<*>) {
        startActivity(
            Intent(this, activityClass)
        )
        finish()
    }
}