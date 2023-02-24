package com.example.netflix.utils

import java.math.BigInteger
import java.security.MessageDigest
import java.util.regex.Pattern

class AuthUtils {

    companion object {
        /**
         * Función que compruba si un ``String`` cumple con el tamaño de __5 a 50__ caracteres.
         * */
        fun hasLength(email: String): Boolean {
            return email.length in 5..50
        }

        /**
         * Con una expresion regular predefinida de ``android.utils.Patters`` verifica si un
         * ``String`` tiene el formato correcto de un email.
         * */
        fun isValidEmail(email: String): Boolean{
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }

        fun isValidPassword(password: String): Boolean {
            return password.matches(
                "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,60}\$".toRegex()
            )
        }

        /**
         * A partir de un String, encripta el mismo con el algoritmo ``SHA-256``.
         *
         * @return El ``String`` encriptado.
         * */
        fun String.sha256(): String {
            val md = MessageDigest.getInstance("SHA-256")
            return BigInteger(1, md.digest(toByteArray()))
                .toString(16)
                .padStart(32, '0')
        }
    }
}