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
        private const val COLUMN_USER_ID = "user_id"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_TABLE = ("CREATE TABLE $TABLE_NAME (" +
                "$COLUMN_ID INTEGER PRIMARY KEY," +
                "$COLUMN_NAME TEXT," +
                "$COLUMN_EMAIL_OR_USERNAME TEXT," +
                "$COLUMN_PASSWORD TEXT," +
                "$COLUMN_NOTES TEXT)" +
                "$COLUMN_USER_ID INTEGER) ")
        db.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertCredential(name: String, emailOrUsername: String, password: String, notes: String, userId: Int): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, name)
            put(COLUMN_EMAIL_OR_USERNAME, emailOrUsername)
            put(COLUMN_PASSWORD, password)
            put(COLUMN_NOTES, notes)
            put(COLUMN_USER_ID, userId)
        }
        return db.insert(TABLE_NAME, null, values)
    }

    fun getAllCredentials(userId: Int): List<Credential> {
        val credentialsList = mutableListOf<Credential>()
        val db = readableDatabase
        val selection = "$COLUMN_USER_ID = ?"
        val selectionArgs = arrayOf(userId.toString())
        val cursor: Cursor? = db.query(TABLE_NAME, null, selection, selectionArgs, null, null, null)
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
}


