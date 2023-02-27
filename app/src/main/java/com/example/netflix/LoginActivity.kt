package com.example.netflix

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.OnFocusChangeListener
import androidx.appcompat.app.AlertDialog
import com.example.netflix.database.UsersHelper
import com.example.netflix.databinding.ActivityLoginBinding
import com.example.netflix.utils.AuthUtils.Companion.sha256

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    lateinit var usersHelper: UsersHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        usersHelper = UsersHelper(applicationContext)

        /* ******************** */
        /* Sección de listeners */
        /* ******************** */

        /*
        * Cambiar el tipo de bacground del campo email dependiendo de si el foco.
        * */
        binding.loginEmail.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus)
                binding.loginEmail.setBackgroundResource(R.drawable.bg_edit_text_focused)
            else
                binding.loginEmail.setBackgroundResource(R.drawable.bg_edit_text)
        }

        /*
        * Cambiar el tipo de bacground del campo email dependiendo de si el foco.
        * */
        binding.loginPassword.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus)
                binding.loginPassword.setBackgroundResource(R.drawable.bg_edit_text_focused)
            else
                binding.loginPassword.setBackgroundResource(R.drawable.bg_edit_text)
        }

        /*
        * Cuando pulse unos de los componentes del array, finaliza esta actividad.
        * */
        val returnComponents = arrayOf(binding.returnButton, binding.subscribeButton)
        returnComponents.forEach {
            it.setOnClickListener { finish() }
        }

        /*
        *
        * */
        binding.loginButtonSignin.setOnClickListener {
            val email = binding.loginEmail.text.toString()
            val password = binding.loginPassword.text.toString()

            val user = usersHelper.checkUserByEmail(email)
            if (user != null) {
                if (password.sha256() != user.passwordEnc) openIncorrectDialog()
                else {
                    startActivity(
                        Intent(this, ProfilesActivity::class.java)
                    )
                }

            } else
                openIncorrectDialog()
        }


    }

    private fun openIncorrectDialog() {
        AlertDialog.Builder(this)
            .setTitle("Email incorrecto")
            .setMessage(getString(R.string.email_error_incorrect))
            .setPositiveButton("CREAR UNA CUENTA NUEVA") { _, _ ->
                finish()
            }
            .setNegativeButton("REINTENTAR") { _, _ -> }
            .create()
            .show()
    }
}