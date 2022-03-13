package com.altwav.finalproject

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_adding_item1.*
import kotlinx.android.synthetic.main.activity_main.*

class AddingItem1 : AppCompatActivity() {
    var userName = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adding_item1)

        btnNext1.setTransformationMethod(null);


        val intent = getIntent()
        userName = intent.getStringExtra("Name").toString()

        val spinner: Spinner = findViewById(R.id.spCategory)
        ArrayAdapter.createFromResource(
            this,
            R.array.category,
            R.layout.custom_spinner
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.customer_spinner_dropdown)
            spinner.adapter = adapter
        }
    }
    fun addingItemNext1(view: View) {
        val foodName = etFoodName.text.toString()
        var foodCategory: String = spCategory.getSelectedItem().toString()

        val intent = Intent(this, AddingItem2::class.java)
        intent.putExtra("FoodName", foodName)
        intent.putExtra("FoodCategory", foodCategory)
        intent.putExtra("Name", userName)
        startActivity(intent)
    }
}