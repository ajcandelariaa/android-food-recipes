package com.altwav.finalproject

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper

class DatabaseRecipe(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){

    companion object{
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "FinalProjectAltWav"
        private const val TABLE_RECIPES = "FoodRecipes"

        private const val KEY_ID = "_id"
        private const val KEY_FOOD_NAME = "foodName"
        private const val KEY_FOOD_CATEGORY= "foodCategory"
        private const val KEY_INGREDIENTS = "ingredients"
        private const val KEY_PROCEDURES = "procedures"
        private const val KEY_ADDITIONAL_INFO = "additionalInfo"
    }

    override fun onCreate(db: SQLiteDatabase?){
        val CREATE_RECIPES_TABLE = ("CREATE TABLE " + TABLE_RECIPES
                + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_FOOD_NAME + " TEXT,"
                + KEY_FOOD_CATEGORY + " TEXT,"
                + KEY_INGREDIENTS + " TEXT,"
                + KEY_PROCEDURES + " TEXT,"
                + KEY_ADDITIONAL_INFO + " TEXT"
                + ")"
                )
        db?.execSQL(CREATE_RECIPES_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS" + TABLE_RECIPES)
        onCreate(db)
    }

    fun addRecipe(recipe: RecipeModelClass): Long {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(KEY_FOOD_NAME, recipe.foodName)
        contentValues.put(KEY_FOOD_CATEGORY, recipe.foodCategory)
        contentValues.put(KEY_INGREDIENTS, recipe.ingredients)
        contentValues.put(KEY_PROCEDURES, recipe.procedures)
        contentValues.put(KEY_ADDITIONAL_INFO, recipe.additionalInfo)

        val success = db.insert(TABLE_RECIPES, null, contentValues)

        db.close()
        return success
    }

    fun viewRecipe(): ArrayList<RecipeModelClass> {
        val recipeList: ArrayList<RecipeModelClass> = ArrayList<RecipeModelClass>()

        val selectQuery = "SELECT * FROM $TABLE_RECIPES"

        val db = this.readableDatabase
        var cursor: Cursor? = null

        try{
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var id: Int
        var foodName: String
        var foodCategory: String
        var ingredients: String
        var procedures: String
        var additionalInfo: String

        if(cursor.moveToFirst()){
            do{
                id = cursor.getInt(cursor.getColumnIndex(KEY_ID))
                foodName = cursor.getString(cursor.getColumnIndex(KEY_FOOD_NAME))
                foodCategory = cursor.getString(cursor.getColumnIndex(KEY_FOOD_CATEGORY))
                ingredients = cursor.getString(cursor.getColumnIndex(KEY_INGREDIENTS))
                procedures = cursor.getString(cursor.getColumnIndex(KEY_PROCEDURES))
                additionalInfo = cursor.getString(cursor.getColumnIndex(KEY_ADDITIONAL_INFO))

                val recipe = RecipeModelClass(
                    id = id,
                    foodName = foodName,
                    foodCategory = foodCategory,
                    ingredients = ingredients,
                    procedures = procedures,
                    additionalInfo = additionalInfo
                )
                recipeList.add(recipe)
            } while (cursor.moveToNext())
        }
        return recipeList
    }

    fun updateRecipe(recipe: RecipeModelClass): Int{
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(KEY_FOOD_NAME, recipe.foodName)
        contentValues.put(KEY_FOOD_CATEGORY, recipe.foodCategory)
        contentValues.put(KEY_INGREDIENTS, recipe.ingredients)
        contentValues.put(KEY_PROCEDURES, recipe.procedures)
        contentValues.put(KEY_ADDITIONAL_INFO, recipe.additionalInfo)

        val success = db.update(TABLE_RECIPES, contentValues, KEY_ID + "=" + recipe.id, null)
        db.close()
        return success
    }

    fun deleteRecipe(recipe: RecipeModelClass): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, recipe.id)

        val success = db.delete(TABLE_RECIPES, KEY_ID + "=" + recipe.id, null)

        db.close()
        return success
    }

}