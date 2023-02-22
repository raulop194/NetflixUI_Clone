package com.example.netflix.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.netflix.model.UserRecord
import java.math.BigInteger
import java.security.MessageDigest

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
     * A partir de un String, encripta el mismo con el algoritmo __SHA-256__.
     * */
    private fun String.sha256(): String {
        val md = MessageDigest.getInstance("SHA-256")
        return BigInteger(1, md.digest(toByteArray()))
            .toString(16)
            .padStart(32, '0')
    }

    fun addUser(email: String, password: String) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COL_EMAIL, email)
            put(COL_PASSWORD, password.sha256())
        }

        db.insert(TABLE_USERS, null, values)
        db.close()
    }

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