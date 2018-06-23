package com.example.bjoerndell.navdraw.Controller

import android.app.ProgressDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.TextView
import android.widget.Toast
import com.example.bjoerndell.navdraw.Model.User
import com.example.bjoerndell.navdraw.R
import com.example.bjoerndell.navdraw.Utilitiy.EXTRA_PASSWORDLENGTH
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_create_user.*



class CreateUser : AppCompatActivity() {

    lateinit var txtEmail: TextView
    lateinit var txtPin: TextView
    lateinit var fbAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_user)

        fbAuth = FirebaseAuth.getInstance()
        val user = fbAuth.currentUser
        if (user != null){
            //User already logged in; start ProfileActivity
            Toast.makeText(this,"USer mit id ${user} registriert", Toast.LENGTH_LONG).show()
            txtIdTest.text = user.uid.toString().trim()
            finish()
            val profileIntent = Intent(this,ProfileUser::class.java)
            profileIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(profileIntent)
        }


        txtEmail = findViewById(R.id.txtEmail)
        txtPin = findViewById(R.id.txtPin)
        fbAuth = FirebaseAuth.getInstance()

        createUserRegisterBtn.setOnClickListener(){
            registerUser()
        }

        loginUserBtn.setOnClickListener(){
            loginUser()
        }
    }

    fun loginUser(){
        val email = txtEmail.text.toString().trim()
        val pin = txtPin.text.toString().trim()


        if(TextUtils.isEmpty(email)){
            txtEmail.error = "Gib deine E-Mail-Adresse ein"
            return
        }
        if (TextUtils.isEmpty(pin)){
            txtPin.error = "Gib eine PIN ein"
            return
        } else if (TextUtils.getTrimmedLength(pin) < EXTRA_PASSWORDLENGTH){
            txtPin.error = "Die PIN muss aus mind. $EXTRA_PASSWORDLENGTH Zahlen bestehen"
            return
        }
        val progress = ProgressDialog(this)
        progress.setMessage("Einloggen...")
        progress.setCancelable(false)
        progress.show()

        fbAuth.signInWithEmailAndPassword(email, pin).addOnCompleteListener {task: Task<AuthResult> ->
            progress.dismiss()
            if(task.isSuccessful){
                Toast.makeText(this,"Du bist eingeloggt", Toast.LENGTH_SHORT).show()
            } else {
                val exception = task.exception?.message
                Toast.makeText(this,exception.toString(), Toast.LENGTH_LONG).show()
            }
        }
    }


    fun registerUser (){

        val email = txtEmail.text.toString().trim()
        val pin = txtPin.text.toString().trim()


        if(TextUtils.isEmpty(email)){
            txtEmail.error = "Gib deine E-Mail-Adresse ein"
            return
        }
        if (TextUtils.isEmpty(pin)){
            txtPin.error = "Gib eine PIN ein"
            return
        } else if (TextUtils.getTrimmedLength(pin) < EXTRA_PASSWORDLENGTH){
            txtPin.error = "Die PIN muss aus mind. $EXTRA_PASSWORDLENGTH Zahlen bestehen"
            return
        }

        val progress = ProgressDialog(this)
        progress.setMessage("Du wirst registriert")
        progress.setCancelable(false)
        progress.show()

        fbAuth.createUserWithEmailAndPassword(email, pin).addOnCompleteListener {task: Task<AuthResult> ->
            progress.dismiss()
            if(task.isSuccessful){
                Toast.makeText(this,"Du bist registriert", Toast.LENGTH_SHORT).show()
                finish()
                val intent = Intent(this, ProfileUser::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
            } else {
                val exception = task.exception?.message
                Toast.makeText(this,exception.toString(), Toast.LENGTH_LONG).show()
            }
        }
    }
}
