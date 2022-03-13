package com.altwav.finalproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_registration_page.*

class RegistrationPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration_page)


        tvBackToLogin.setOnClickListener{
            val intent = Intent(this, LoginPage::class.java)
            startActivity(intent)
        }

        btnRegister.setOnClickListener {
            val registerName = etRegisterName.text.toString()
            val registerUsername = etRegisterUsername.text.toString()
            val registerPassword = etRegisterpassword.text.toString()

            val databaseUsers: DatabaseUsers = DatabaseUsers(this)
            if(registerName.isNotEmpty() && registerUsername.isNotEmpty() && registerPassword.isNotEmpty()){
                if (!databaseUsers!!.isUsernameExists(registerUsername)){
                    //Email does not exist now add new user to database
                    val status =
                        databaseUsers.addUser(LoginModelClass(0, registerName, registerUsername, registerPassword))
                    if(status > -1){
                        Toast.makeText(this, "Record Saved", Toast.LENGTH_LONG).show()
                        val intent = Intent(this, LoginPage::class.java)
                        startActivity(intent)
                    }
                } else {
                    Toast.makeText(this, "Username is taken, Please user another.", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(this, "Please fill the form to register.", Toast.LENGTH_LONG).show()
            }

        }
    }

}