package com.linkiaM13G3.akmAndroidClient.Pages

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.ContentValues
import android.database.Cursor



class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "Credentials.db"
        private const val TABLE_NAME = "credentials"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_EMAIL_OR_USERNAME = "email_or_username"
        private const val COLUMN_PASSWORD = "password"
        private const val COLUMN_NOTES = "notes"
        private const val COLUMN_TYPE = "type"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_TABLE = ("CREATE TABLE $TABLE_NAME (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COLUMN_NAME TEXT," +
                "$COLUMN_EMAIL_OR_USERNAME TEXT UNIQUE," +
                "$COLUMN_PASSWORD TEXT," +
                "$COLUMN_NOTES TEXT," +
                "TYPE TEXT)") // Nueva columna para el tipo de credencial
        db.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Aquí es donde actualizas tu esquema de base de datos si has incrementado DATABASE_VERSION
        if (oldVersion < 2) {
            // Por ejemplo, si la actualización es desde una versión anterior a la 2,
            // añades la nueva columna 'type' a la tabla 'credentials'.
            val ADD_COLUMN_TYPE = "ALTER TABLE $TABLE_NAME ADD COLUMN $COLUMN_TYPE TEXT"
            db.execSQL(ADD_COLUMN_TYPE)
        }
    }
    fun insertCredential(name: String, emailOrUsername: String, password: String, notes: String, type: String): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, name)
            put(COLUMN_EMAIL_OR_USERNAME, emailOrUsername)
            put(COLUMN_PASSWORD, password)
            put(COLUMN_NOTES, notes)
            put("TYPE", type)
        }
        return db.insertWithOnConflict(TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_IGNORE)
    }

    fun getAllCredentials(): List<Credential> {
        val credentialsList = mutableListOf<Credential>()
        val db = readableDatabase
        val cursor: Cursor? = db.query(TABLE_NAME, null, null, null, null, null, null)
        cursor?.use {
            while (it.moveToNext()) {
                val id = it.getInt(it.getColumnIndexOrThrow(COLUMN_ID))
                val name = it.getString(it.getColumnIndexOrThrow(COLUMN_NAME))
                val emailOrUsername = it.getString(it.getColumnIndexOrThrow(COLUMN_EMAIL_OR_USERNAME))
                val password = it.getString(it.getColumnIndexOrThrow(COLUMN_PASSWORD))
                val notes = it.getString(it.getColumnIndexOrThrow(COLUMN_NOTES))
                credentialsList.add(Credential(id, name, emailOrUsername, password, notes))
            }
        }
        return credentialsList
    }

    fun updateCredential(credentialId: Int, newPassword: String) {
        val db = this.writableDatabase


        val values = ContentValues().apply {
            put(COLUMN_PASSWORD, newPassword)
        }


        val selection = "$COLUMN_ID = ?"
        val selectionArgs = arrayOf(credentialId.toString())


        db.update(
            TABLE_NAME,
            values,
            selection,
            selectionArgs
        )
    }
    fun userCanLogIn(emailOrUsername: String, password: String): Boolean {
        val db = this.readableDatabase
        val projection = arrayOf(COLUMN_ID) // Solo necesitamos el ID para verificar la existencia
        val selection = "$COLUMN_EMAIL_OR_USERNAME = ? AND $COLUMN_PASSWORD = ?"
        val selectionArgs = arrayOf(emailOrUsername, password)
        val cursor = db.query(TABLE_NAME, projection, selection, selectionArgs, null, null, null)
        val userExists = cursor.moveToFirst()
        cursor.close()
        return userExists
    }
}

