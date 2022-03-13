package com.altwav.finalproject

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseUsers(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){

    companion object{
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "FinalProjectAltWav2"
        private const val TABLE_USERS = "Users"

        private const val KEY_ID = "_id"
        private const val KEY_NAME = "name"
        private const val KEY_USERNAME= "username"
        private const val KEY_PASSWORD = "password"
    }

    override fun onCreate(db: SQLiteDatabase?){
        val CREATE_RECIPES_TABLE = ("CREATE TABLE " + TABLE_USERS
                + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_NAME + " TEXT,"
                + KEY_USERNAME + " TEXT,"
                + KEY_PASSWORD + " TEXT"
                + ")"
                )
        db?.execSQL(CREATE_RECIPES_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS" + TABLE_USERS)
        onCreate(db)
    }

    fun addUser(recipeLoginModelClass: LoginModelClass): Long {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(KEY_NAME, recipeLoginModelClass.name)
        contentValues.put(KEY_USERNAME, recipeLoginModelClass.username)
        contentValues.put(KEY_PASSWORD, recipeLoginModelClass.password)

        val success = db.insert(TABLE_USERS, null, contentValues)

        db.close()
        return success
    }

    fun isUsernameExists(email: String): Boolean {
        val db = this.readableDatabase
        val cursor = db.query(
            DatabaseUsers.TABLE_USERS,
            arrayOf(
                DatabaseUsers.KEY_ID,
                DatabaseUsers.KEY_NAME,
                DatabaseUsers.KEY_USERNAME,
                DatabaseUsers.KEY_PASSWORD
            ),  //Selecting columns want to query
            "${DatabaseUsers.KEY_USERNAME}=?",
            arrayOf(email),  //Where clause
            null,
            null,
            null
        )
        return if (cursor != null && cursor.moveToFirst()) {
            true
        } else
            false
    }

    fun checkusernamepassword( username: String, password: String): Boolean? {
        val db = this.writableDatabase
        val cursor = db.rawQuery(
            "Select * from users where username = ? and password = ?",
            arrayOf(username, password)
        )
        return if (cursor.count > 0) true else false
    }
}