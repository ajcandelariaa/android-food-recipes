package com.altwav.finalproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login_page.*

class LoginPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)

        tvNoAccountLink.setOnClickListener{
            val intent = Intent(this, RegistrationPage::class.java)
            startActivity(intent)
        }


       btnLogin.setOnClickListener {
            val loginUsername = etLoginUsername.text.toString()
            val loginPassword = etLoginPassword.text.toString()

           val databaseUsers: DatabaseUsers = DatabaseUsers(this)


           if(loginUsername.equals("") || loginPassword.equals("")) {
               Toast.makeText(this, "Please enter all the fields", Toast.LENGTH_SHORT).show()
           } else{
               val checkuserpass = databaseUsers.checkusernamepassword(loginUsername, loginPassword)
               if(checkuserpass == true){
                   etLoginUsername.setText("")
                   etLoginPassword.setText("")
                   Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show()
                   var intent  = Intent(this, MainActivity::class.java)
                   intent.putExtra("Name", loginUsername)
                   startActivity(intent)
               } else {
                   Toast.makeText(this, "Invalid Username and Password", Toast.LENGTH_SHORT).show()
               }
           }
       }
    }
}