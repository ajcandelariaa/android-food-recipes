package com.altwav.finalproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_adding_item1.*
import kotlinx.android.synthetic.main.activity_adding_item2.*

class AddingItem2 : AppCompatActivity() {
    var userName = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adding_item2)

        btnNext2.setTransformationMethod(null);

        val intent = getIntent()
        val foodName = intent.getStringExtra("FoodName")
        val foodCategory = intent.getStringExtra("FoodCategory")
        userName = intent.getStringExtra("Name").toString()

        tvFoodName1.text = foodName
        tvCategory1.text = foodCategory
    }

    fun addingItemNext2(view: View) {
        val foodName = tvFoodName1.text.toString()
        var foodCategory: String =  tvCategory1.text.toString()
        val ingredients = etIngredients.text.toString()

        val intent = Intent(this, AddingItem3::class.java)
        intent.putExtra("FoodName", foodName)
        intent.putExtra("FoodCategory", foodCategory)
        intent.putExtra("Ingredients", ingredients)
        intent.putExtra("Name", userName)
        startActivity(intent)
    }
}