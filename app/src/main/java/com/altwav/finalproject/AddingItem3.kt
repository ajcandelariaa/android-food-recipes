package com.altwav.finalproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_adding_item2.*
import kotlinx.android.synthetic.main.activity_adding_item3.*

class AddingItem3 : AppCompatActivity() {
    var userName = ""
    var ingredients = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adding_item3)

        btnNext3.setTransformationMethod(null);

        val intent = getIntent()
        val foodName = intent.getStringExtra("FoodName")
        val foodCategory = intent.getStringExtra("FoodCategory")
        ingredients = intent.getStringExtra("Ingredients").toString()
        userName = intent.getStringExtra("Name").toString()

        tvFoodName2.text = foodName
        tvCategory2.text = foodCategory
    }

    fun addingItemNext3(view: View) {
        val foodName = tvFoodName2.text.toString()
        var foodCategory: String =  tvCategory2.text.toString()
        val procedures = etProcedures.text.toString()

        val intent = Intent(this, AddingItem4::class.java)
        intent.putExtra("FoodName", foodName)
        intent.putExtra("FoodCategory", foodCategory)
        intent.putExtra("Ingredients", ingredients)
        intent.putExtra("Procedures", procedures)
        intent.putExtra("Name", userName)
        startActivity(intent)
    }
}