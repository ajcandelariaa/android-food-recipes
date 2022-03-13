package com.altwav.finalproject

import android.content.Intent
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_recipe_api.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class RecipeApi : AppCompatActivity() {
    private var mTextViewResult: TextView? = null

    var userName = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_api)
        val textView = findViewById<TextView>(R.id.text_view_result)


        val intent = getIntent()
        userName = intent.getStringExtra("Name").toString()

        tvRecipeApiBack.setTransformationMethod(null);

        tvRecipeApiBack.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("Name", userName)
            startActivity(intent)
        }

        val queue = Volley.newRequestQueue(this)
        val url = "https://next.json-generator.com/api/json/get/V19m1cvS5"

        val stringRequest = StringRequest( Request.Method.GET, url,
            { response ->
                try {
                    val json_obj: JSONObject = JSONObject(response.toString())
                    var arr_items: JSONArray = json_obj.getJSONArray("results")
                    for (i in 0 until arr_items.length()) {
                        var item: JSONObject = arr_items.getJSONObject(i)
                        var title = item.getString("title")
                        var ingredients = item.getString("ingredients")
                        textView.append("RECIPE: $title \nINGREDIENTS:\n$ingredients \n\n")
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            { textView.text = "That didn't work!" })

        // Add the request to the RequestQueue.
        queue.add(stringRequest)
        // scroll textview
        textView.movementMethod = ScrollingMovementMethod()

    }
}