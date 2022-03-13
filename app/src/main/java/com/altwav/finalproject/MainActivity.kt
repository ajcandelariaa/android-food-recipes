package com.altwav.finalproject

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_update.*

class MainActivity : AppCompatActivity() {
    var userName = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intent = getIntent()
        val foodName = intent.getStringExtra("FoodName")
        val foodCategory = intent.getStringExtra("FoodCategory")
        val ingredients = intent.getStringExtra("Ingredients")
        val procedures = intent.getStringExtra("Procedures")
        val additionalInfo = intent.getStringExtra("AdditionalInfo")
        userName = intent.getStringExtra("Name").toString()
        tvUserName.setText(userName)



        btnAddRecipe.setTransformationMethod(null);

        btnAddRecipe.setOnClickListener {
            val intent = Intent(this, AddingItem1::class.java)
            intent.putExtra("Name", userName)
            startActivity(intent)
        }

        tvAdditionalInfo.setOnClickListener{
            val intent = Intent(this, RecipeApi::class.java)
            intent.putExtra("Name", userName)
            startActivity(intent)
        }

        tvLogout.setOnClickListener{
            val intent = Intent(this, LoginPage::class.java)
            startActivity(intent)
        }

        Log.i("msg", "FoodName: $foodName")
        Log.i("msg", "FoodCategory: $foodCategory")
        Log.i("msg", "Ingredients: $ingredients")
        Log.i("msg", "Procedures: $procedures")
        Log.i("msg", "AdditionalInfo: $additionalInfo")

        setupListofDataIntoRecyclerView()
    }

    private fun setupListofDataIntoRecyclerView(){
        if(getItemsList().size > 0) {
            rvItemsList.visibility = View.VISIBLE
            tvNoRecordsAvailable.visibility = View.GONE

            rvItemsList.layoutManager = LinearLayoutManager(this)
            val itemAdapter = ItemAdapter(this, getItemsList())
            rvItemsList.adapter = itemAdapter
        } else {
            rvItemsList.visibility = View.GONE
            tvNoRecordsAvailable.visibility = View.VISIBLE
        }
    }

    private fun getItemsList(): ArrayList<RecipeModelClass> {
        val databaseRecipe: DatabaseRecipe = DatabaseRecipe(this)
        return databaseRecipe.viewRecipe()
    }


    fun updateRecordDialog(recipeModelClass: RecipeModelClass){
        val updateDialog = Dialog(this, R.style.Theme_Dialog)
        updateDialog.setCancelable(false)
        updateDialog.setContentView(R.layout.dialog_update)

        updateDialog.etUpdateFoodName.setText(recipeModelClass.foodName)
        updateDialog.etUpdateFoodCategory.setText(recipeModelClass.foodCategory)
        updateDialog.etUpdateIngredients.setText(recipeModelClass.ingredients)
        updateDialog.etUpdateProcedures.setText(recipeModelClass.procedures)
        updateDialog.etUpdateAdditionalInfo.setText(recipeModelClass.additionalInfo)

        updateDialog.tvUpdate.setOnClickListener{
            val foodName = updateDialog.etUpdateFoodName.text.toString()
            val foodCategory = updateDialog.etUpdateFoodCategory.text.toString()
            val ingredients = updateDialog.etUpdateIngredients.text.toString()
            val procedures = updateDialog.etUpdateProcedures.text.toString()
            val additionalInfo = updateDialog.etUpdateAdditionalInfo.text.toString()

            val databaseRecipe: DatabaseRecipe = DatabaseRecipe(this)

            if(foodName.isNotEmpty() && foodCategory.isNotEmpty()){
                val status =
                    databaseRecipe.updateRecipe(RecipeModelClass(recipeModelClass.id, foodName, foodCategory, ingredients, procedures, additionalInfo))
                if(status > -1){
                    Toast.makeText(this, "Recipe Updated", Toast.LENGTH_LONG).show()
                    setupListofDataIntoRecyclerView()
                    updateDialog.dismiss()
                }
            } else {
                Toast.makeText(this, "Food Name or Food Category cannot be empty!", Toast.LENGTH_LONG).show()
            }
        }
        updateDialog.tvCancel.setOnClickListener{
            updateDialog.dismiss()
        }
        updateDialog.show()
    }

    fun deleteRecordAlertDialog(recipeModelClass: RecipeModelClass) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Delete Record")
        builder.setMessage("Are you sure you wants to delete ${recipeModelClass.foodName}.")
        builder.setIcon(android.R.drawable.ic_dialog_alert)

        builder.setPositiveButton("Yes") { dialogInterface, which ->

            val databaseRecipe: DatabaseRecipe = DatabaseRecipe(this)
            val status = databaseRecipe.deleteRecipe(RecipeModelClass(recipeModelClass.id,  "", "", "", "", ""))
            if (status > -1) {
                Toast.makeText(
                    applicationContext,
                    "Record deleted successfully.",
                    Toast.LENGTH_LONG
                ).show()

                setupListofDataIntoRecyclerView()
            }
            dialogInterface.dismiss()
        }
        builder.setNegativeButton("No") { dialogInterface, which ->
            dialogInterface.dismiss()
        }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

}