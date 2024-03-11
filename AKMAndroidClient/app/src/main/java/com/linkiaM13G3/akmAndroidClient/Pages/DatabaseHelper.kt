package com.linkiaM13G3.akmAndroidClient.Pages

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.ContentValues
import android.util.Log

class DatabaseHelper private constructor(context: Context) : SQLiteOpenHelper(context.applicationContext, DATABASE_NAME, null, DATABASE_VERSION) {
    data class App(val id: Int, val name: String, val usernameOrEmail: String, val password: String)
    companion object {
        private const val DATABASE_VERSION = 4
        private const val DATABASE_NAME = "Credentials.db"

        private const val TABLE_USERS = "users"
        private const val COLUMN_USER_ID = "id"
        private const val COLUMN_USER_FIRST_NAME = "first_name"
        private const val COLUMN_USER_LAST_NAME = "last_name"
        private const val COLUMN_USER_EMAIL = "email"
        private const val COLUMN_USER_PASSWORD = "password"


        private const val TABLE_APPS = "apps"
        private const val COLUMN_APP_ID = "id"
        private const val COLUMN_APP_NAME = "name"
        private const val COLUMN_APP_USERNAME_OR_EMAIL = "username_or_email"
        private const val COLUMN_APP_PASSWORD = "password"

        private const val TABLE_CREDENTIALS = "credentials"
        private const val COLUMN_CREDENTIALS_ID = "id"
        private const val COLUMN_CREDENTIALS_TYPE = "type"
        private const val COLUMN_CREDENTIALS_USERNAME = "username"
        private const val COLUMN_CREDENTIALS_PASSWORD = "password"

        @Volatile
        private var INSTANCE: DatabaseHelper? = null

        fun getInstance(context: Context): DatabaseHelper =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: DatabaseHelper(context).also { INSTANCE = it }
                }
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Creación de la tabla de usuarios, sin cambios aquí
        val CREATE_USERS_TABLE = ("CREATE TABLE $TABLE_USERS (" +
                "$COLUMN_USER_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COLUMN_USER_FIRST_NAME TEXT," +
                "$COLUMN_USER_LAST_NAME TEXT," +
                "$COLUMN_USER_EMAIL TEXT UNIQUE," +
                "$COLUMN_USER_PASSWORD TEXT)")
        db.execSQL(CREATE_USERS_TABLE)

        // Modificación de la tabla de aplicaciones para incluir user_id
        val CREATE_APPS_TABLE = ("CREATE TABLE $TABLE_APPS (" +
                "$COLUMN_APP_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COLUMN_APP_NAME TEXT," +
                "$COLUMN_APP_USERNAME_OR_EMAIL TEXT," +
                "$COLUMN_APP_PASSWORD TEXT," +
                "user_id INTEGER, " + // Añadir esta línea
                "FOREIGN KEY(user_id) REFERENCES $TABLE_USERS($COLUMN_USER_ID))") // Asegura la relación
        db.execSQL(CREATE_APPS_TABLE)

        // Creación de la tabla de credenciales, sin cambios aquí
        val CREATE_CREDENTIALS_TABLE = ("CREATE TABLE $TABLE_CREDENTIALS (" +
                "$COLUMN_CREDENTIALS_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COLUMN_CREDENTIALS_TYPE TEXT)")
        db.execSQL(CREATE_CREDENTIALS_TABLE)

        Log.d("DatabaseHelper", "Tables created successfully: $TABLE_USERS, $TABLE_APPS, $TABLE_CREDENTIALS")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if (oldVersion < 5) {
            // Ejemplo de cómo manejar actualizaciones. Aquí deberías manejar la actualización real.
        }
    }

    // Métodos de ejemplo para insertar y recuperar datos de las tablas
    // Asegúrate de implementar y adaptar estos métodos según las necesidades específicas de tu aplicación

    fun insertUser(firstName: String, lastName: String, email: String, password: String): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_USER_FIRST_NAME, firstName)
            put(COLUMN_USER_LAST_NAME, lastName)
            put(COLUMN_USER_EMAIL, email)
            put(COLUMN_USER_PASSWORD, password)
        }
        return db.insertWithOnConflict(TABLE_USERS, null, values, SQLiteDatabase.CONFLICT_IGNORE)
    }

    fun insertApp(userId: Int, name: String, usernameOrEmail: String, password: String): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put("name", name)
            put("username_or_email", usernameOrEmail)
            put("password", password)
            put("user_id", userId) // Añadir el userId al ContentValues
        }
        return db.insertWithOnConflict(TABLE_APPS, null, values, SQLiteDatabase.CONFLICT_IGNORE)
    }

    fun getAllAppsByUserId(userId: Int): List<App> {
        val appsList = mutableListOf<App>()
        val db = this.readableDatabase
        val cursor = db.query(TABLE_APPS, null, "user_id = ?", arrayOf(userId.toString()), null, null, null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_APP_ID))
                val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_APP_NAME))
                val usernameOrEmail = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_APP_USERNAME_OR_EMAIL))
                val password = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_APP_PASSWORD))
                appsList.add(App(id, name, usernameOrEmail, password))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return appsList
    }

    // Agrega métodos para recuperar y actualizar datos como se necesite

    fun userCanLogIn(email: String, password: String): Boolean {
        val db = this.readableDatabase
        val selection = "$COLUMN_USER_EMAIL = ? AND $COLUMN_USER_PASSWORD = ?"
        val selectionArgs = arrayOf(email, password)
        val cursor = db.query(TABLE_USERS, null, selection, selectionArgs, null, null, null)
        val userExists = cursor.moveToFirst()
        cursor.close()
        return userExists
    }

    fun getAllApps(): List<App> {
        val appsList = mutableListOf<App>()
        val db = readableDatabase
        val cursor = db.query(TABLE_APPS, null, null, null, null, null, null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_APP_ID))
                val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_APP_NAME))
                val usernameOrEmail = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_APP_USERNAME_OR_EMAIL))
                val password = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_APP_PASSWORD))
                appsList.add(App(id, name, usernameOrEmail, password))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return appsList
    }
    fun getUserIdForLogin(emailOrUsername: String, password: String): Int {
        val db = this.readableDatabase
        var userId = -1

        // Aquí, asumimos que la columna para username y email es la misma. Si tienes columnas separadas,
        // necesitarás ajustar la consulta SQL de acuerdo a tu esquema de base de datos.
        val cursor = db.query(
                TABLE_USERS, // La tabla a consultar
                arrayOf(COLUMN_USER_ID), // Las columnas a retornar
                "$COLUMN_USER_EMAIL = ? AND $COLUMN_USER_PASSWORD = ?", // La cláusula WHERE
                arrayOf(emailOrUsername, password), // Los valores para la cláusula WHERE
                null, // group by
                null, // having
                null  // order by
        )

        if (cursor.moveToFirst()) {
            userId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_USER_ID))
        }
        cursor.close()

        return userId
    }

}