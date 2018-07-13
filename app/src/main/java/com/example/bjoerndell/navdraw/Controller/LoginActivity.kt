package com.example.bjoerndell.navdraw.Controller

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import com.example.bjoerndell.navdraw.R
import com.example.bjoerndell.navdraw.Utilitiy.EXTRA_PASSWORDLENGTH
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

class LoginActivity : AppCompatActivity() {


    private lateinit var fbAuth: FirebaseAuth
    lateinit var email: EditText
    lateinit var pin: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        email = findViewById(R.id.loginActEmailTxt)
        pin = findViewById(R.id.loginActPinTxt)

        loginActLoginBtn.setOnClickListener(){
            loginUser()
        }

        loginActPwResetTxt.setOnClickListener(){
            var dialog: AlertDialog
            val builder = AlertDialog.Builder(this)
                    .setTitle("PIN zurücksetzen")
                    .setMessage("Möchtest du deine PIN zurücksetzen")

            val dialogListener = DialogInterface.OnClickListener{_, btn ->
                when (btn){
                    DialogInterface.BUTTON_POSITIVE -> {
                        restPassword()
                    }
                    DialogInterface.BUTTON_NEGATIVE -> {
                    }
                }
            }
            builder.setPositiveButton("Ja",dialogListener)
            builder.setNegativeButton("Nein", dialogListener)

            dialog = builder.create()
            dialog.show()
        }

        loginActRegisterBtn.setOnClickListener(){
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

    }

    fun restPassword(){

        if(TextUtils.isEmpty(email.text.toString())){
            loginActEmailTxt.error = "Bitte gib deine E-Mail-Adresse ein"
            return
        }

        fbAuth = FirebaseAuth.getInstance()
        fbAuth.sendPasswordResetEmail(email.text.toString()).addOnCompleteListener {
            if (it.isSuccessful){
                Toast.makeText(this, "E-Mail wurde versendet", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, it.exception!!.message.toString(), Toast.LENGTH_LONG).show()
            }
        }
    }

    fun loginUser(){
        //val email = loginActEmailTxt.text.toString().trim()
        //val pin = loginActPinTxt.text.toString().trim()

        if(TextUtils.isEmpty(email.text.toString())){
            loginActEmailTxt.error = "Bitte gib deine E-Mail-Adresse ein"
            return
        }
        if (TextUtils.isEmpty(pin.text.toString())){
            loginActPinTxt.error = "Bitte gib deine PIN ein"
            return
        }
        val progress = ProgressDialog(this)
        progress.setMessage("einloggen...")
        progress.setCancelable(false)
        progress.show()

        fbAuth = FirebaseAuth.getInstance()
        fbAuth.signInWithEmailAndPassword(email.text.toString(), pin.text.toString()).addOnCompleteListener {task: Task<AuthResult> ->
            progress.dismiss()
            if(task.isSuccessful){
                Toast.makeText(this,"Welcome back!", Toast.LENGTH_SHORT).show()
                finish()
                val profileIntent = Intent(this,UserProfileActivity::class.java)
                profileIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(profileIntent)
            } else {
                val exception = task.exception?.message
                Toast.makeText(this,exception.toString(), Toast.LENGTH_LONG).show()
            }
        }
    }

}
