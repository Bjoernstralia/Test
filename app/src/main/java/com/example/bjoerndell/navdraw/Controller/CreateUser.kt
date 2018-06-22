package com.example.bjoerndell.navdraw.Controller

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.example.bjoerndell.navdraw.Model.User
import com.example.bjoerndell.navdraw.R
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_create_user.*
import org.w3c.dom.Text

class CreateUser : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_user)

        createUserRegisterBtn.setOnClickListener(){
            val firstName = findViewById<TextView>(R.id.txtFirstNameReg)
            val lastName = findViewById<TextView>(R.id.txtLastNameReg)

            if (firstName.text.toString().isEmpty()) {
                firstName.error = "Bitte Vornamen eingeben"
            } else if (lastName.text.toString().isEmpty()) {
                lastName.error = "Bitte Nachnamen eingeben"
            } else {
                createNewUser(firstName.text.toString(), lastName.text.toString())

                //Textfelder zurücksetzen
                firstName.text = null
                lastName.text = null
            }
        }

    }

    fun createNewUser(firstName: String, lastName: String){

        val ref = FirebaseDatabase.getInstance().getReference("User")

        val userId = ref.push().key
        val user = User(userId, firstName, lastName, "ohne")

        ref.child(userId).setValue(user).addOnCompleteListener(){
            Toast.makeText(applicationContext, "Benutzer wurde angelegt!", Toast.LENGTH_SHORT).show()

        }



    }

}
