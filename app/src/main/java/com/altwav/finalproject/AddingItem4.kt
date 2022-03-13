package com.altwav.finalproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_adding_item4.*

class AddingItem4 : AppCompatActivity() {
    var userName = ""
    var ingredients = ""
    var procedures = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adding_item4)

        btnFinish.setTransformationMethod(null);

        val intent = getIntent()
        val foodName = intent.getStringExtra("FoodName")
        val foodCategory = intent.getStringExtra("FoodCategory")
        ingredients = intent.getStringExtra("Ingredients").toString()
        procedures = intent.getStringExtra("Procedures").toString()
        userName = intent.getStringExtra("Name").toString()

        tvFoodName3.text = foodName
        tvCategory3.text = foodCategory
    }

    fun addingItemFinish(view: View) {
        val foodName = tvFoodName3.text.toString()
        var foodCategory: String =  tvCategory3.text.toString()
        val additionalInfo = etNotes.text.toString()

        val databaseRecipe: DatabaseRecipe = DatabaseRecipe(this)
        if(!foodName.isEmpty() && !foodCategory.isEmpty()){
            val status =
                databaseRecipe.addRecipe(RecipeModelClass(0, foodName, foodCategory, ingredients, procedures, additionalInfo))
            if(status > -1){
                Toast.makeText(this, "Record Saved", Toast.LENGTH_LONG).show()
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("Name", userName)
                startActivity(intent)
            }
        } else {
            Toast.makeText(this, "Food Name or Food Category cannot be empty!", Toast.LENGTH_LONG).show()
        }
    }
}