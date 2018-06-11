package com.example.bjoerndell.navdraw.Controller

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.bjoerndell.navdraw.R
import kotlinx.android.synthetic.main.activity_create_user.*

class CreateUser : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_user)

        createUserRegisterBtn.setOnClickListener{
            createNewUser()
        }

    }

    fun createNewUser(){
        val text = "Benutzer wird registriert!"
        val duration = Toast.LENGTH_SHORT

        val toast = Toast.makeText(applicationContext, text, duration)
        toast.show()
    }

}
