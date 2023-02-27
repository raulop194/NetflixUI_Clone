package com.example.netflix.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.netflix.model.UserRecord
import com.example.netflix.utils.AuthUtils.Companion.sha256

/**
 * Clase que proporciona ayuda a la hora de crear y establecer una conexion con un base de datos
 * local de tipo ``SQLite``
 *
 * @author Raúl López-Bravo de Castro
 * */
class UsersHelper(context: Context): SQLiteOpenHelper(context, DATABASE, null, DATABASE_VERSION) {

    // Propiedades de la base de datos
    companion object {
        private const val DATABASE_VERSION = 2
        private const val DATABASE = "Users.db"
        private const val TABLE_USERS = "user"
        private const val COL_EMAIL = "email"
        private const val COL_PASSWORD = "password"
    }

    /**
     * Añade un nuevo usuario a la tabla ``user``.
     *
     * @param email Correo electronico del nuevo usuario. El campo es unico y no nulo.
     *
     * @param password Contraseña en crudo del nuevo usuario.
     * Dentro esta se encripta en ``SHA-256``.
     *
     * @return En numero de registros (o filas) afectadas en la tabla. Devolvera -1 si hay un error.
     * */
    fun addUser(email: String, password: String): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COL_EMAIL, email)
            put(COL_PASSWORD, password.sha256())
        }

        val result = db.insert(TABLE_USERS, null, values)
        db.close()

        return result
    }

    /**
     * Obtiene un usuario de la tabla ``usuario`` que coincida con el email del usuario
     * a consultar.
     *
     * @param email Email a buscar
     * @return El usuario deseado. Retorna nulo en caso de que no exista el usuario.
     * */
    @SuppressLint("Recycle", "Range")
    fun checkUserByEmail(email: String): UserRecord? {
        var userRecord: UserRecord? = null
        val db = this.readableDatabase
        val selectUser = "SELECT * FROM $TABLE_USERS WHERE $COL_EMAIL = '$email'"
        val cursor = db.rawQuery(selectUser, null)

        if (cursor.moveToFirst()) {
            userRecord = UserRecord(
                cursor.getString(cursor.getColumnIndex(COL_EMAIL)),
                cursor.getString(cursor.getColumnIndex(COL_PASSWORD))
            )
        }

        cursor.close()
        db.close()
        return userRecord
    }

    // Cuando ocurre en evento, se crea la tabla `user`
    override fun onCreate(db: SQLiteDatabase) {
        val createTable = "" +
                "CREATE TABLE $TABLE_USERS (" +
                "$COL_EMAIL TEXT PRIMARY KEY," +
                "$COL_PASSWORD TEXT" +
                ")"

        db.execSQL(createTable)
    }

    // Cuando ocurre el evento, se destruye y se vulve a crear la tabla `user`
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        val dropTable = "DROP TABLE IF EXISTS $TABLE_USERS"
        db.execSQL(dropTable)
        onCreate(db)
    }
}