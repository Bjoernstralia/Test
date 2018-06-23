package com.example.bjoerndell.navdraw.Controller

import android.app.ProgressDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.EditText
import android.widget.Toast
import com.example.bjoerndell.navdraw.Model.User
import com.example.bjoerndell.navdraw.R
import com.example.bjoerndell.navdraw.Utilitiy.CHOOSE_IMAGE
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_create_user.*
import kotlinx.android.synthetic.main.activity_profile_user.*

class ProfileUser : AppCompatActivity() {


    lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_user)

        mAuth = FirebaseAuth.getInstance()

        val user = mAuth.currentUser
        if (user != null){
            //User already logged in; start ProfileActivity
            txtUid.text = user.uid.toString().trim()
        } else {
            txtUid.text = "Kein User gefunden"
        }

        updateProfileUserBtn.setOnClickListener(){

            updateUserInformation()
        }

    }

    private fun updateUserInformation(){

        val user = mAuth.currentUser
        val vorname = txtVorname.text.toString().trim()
        val nachname = txtNachname.text.toString().trim()

        if(user == null){
            Toast.makeText(this,"Es ist kein Benutzer eingeloggt", Toast.LENGTH_LONG).show()
            return
        }
        if (TextUtils.isEmpty(vorname)){
            txtVorname.error = "Feld leer"
            return
        }
        if(TextUtils.isEmpty(nachname)){
            txtNachname.error="Feld leer"
            return
        }

        val progress = ProgressDialog(this)
        progress.setMessage("Profil wird erweitert...")
        progress.setCancelable(false)
        progress.show()


        val newUser = User(vorname,nachname,"placeholder_person", user!!.uid)

        val fbDataBase = FirebaseDatabase.getInstance().getReference("Users")

        fbDataBase.child(user!!.uid).setValue(newUser).addOnCompleteListener(){
                    progress.dismiss()
                    if (it.isSuccessful){
                        Toast.makeText(this, "Profil aktualisiert", Toast.LENGTH_LONG).show()
                        mAuth.signOut()
                        finish()

                    } else {
                        Toast.makeText(this, it.exception?.message.toString(), Toast.LENGTH_LONG).show()
                    }
        }


        //TEST START
        val mMobileArray = ArrayList<User>()
        fbDataBase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot?) {
                val tt = snapshot!!.children
                tt.forEach(){
                    println("#Logging: ${it.value.toString()}")
                }
            }
            override fun onCancelled(p0: DatabaseError?) {
            }
        })
        //TEST ENDE


    }
}



