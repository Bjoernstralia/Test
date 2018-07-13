package com.example.bjoerndell.navdraw.Controller

import android.app.ProgressDialog
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.text.TextUtils
import android.widget.TextView
import android.widget.Toast
import com.example.bjoerndell.navdraw.Model.User
import com.example.bjoerndell.navdraw.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_user_profile.*
import kotlinx.android.synthetic.main.app_bar_main.*

class UserProfileActivity : AppCompatActivity() {

    lateinit var fbAuth: FirebaseAuth
    lateinit var fbDb: FirebaseDatabase
    lateinit var fbUser: FirebaseUser

    //fields on XML
    lateinit var firstName: TextView
    lateinit var lastName: TextView
    var compareFirstName = ""
    var compareLastName = ""

    private lateinit var progress: ProgressDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        fbAuth = FirebaseAuth.getInstance()
        fbDb = FirebaseDatabase.getInstance()

        firstName = findViewById(R.id.userProfileActFirstN)
        lastName = findViewById(R.id.userProfileActLastN)

        if (fbAuth.currentUser != null){
            fbUser = fbAuth.currentUser!!
            showProgressHelper(this, "Benutzerdaten laden....")
            getUserData()
        } else {
            finish()
        }

        userProfileActUpdateBtn.setOnClickListener{
            updateUserData()
        }

        userProfileActCancelBtn.setOnClickListener(){
            finish()
        }
    }

    private fun getUserData(){

        val fbRef = fbDb.getReference("Users").child(fbUser.uid)

        fbRef.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onCancelled(error: DatabaseError?) {
                progress.dismiss()
            }

            override fun onDataChange(snapshot: DataSnapshot?) {
                if (snapshot!!.exists()) {
                    val user = snapshot.getValue<User>(User::class.java)
                    firstName.text = user!!.name
                    lastName.text = user.lastName
                    compareFirstName = user.name
                    compareLastName = user.lastName
                }
                progress.dismiss()
            }
        })
    }

    private fun updateUserData(){

        if (!checkFields()) return //Wenn Funktion false dann stop
        showProgressHelper(this, "Benutzerdaten speichern....")

        val fbRef = fbDb.getReference("Users")

        //Update Auth
        val profileUpdate = UserProfileChangeRequest.Builder()
                .setDisplayName("${firstName.toString().trim()} ${lastName.text.toString().trim()}")
                .build()
        fbUser.updateProfile(profileUpdate).addOnCompleteListener {

            if(it.isSuccessful) {
                //Update Database
                val dbUserEntry = User(firstName.text.toString().trim(), lastName.text.toString().trim(), "placeholder_person", fbUser.uid, fbUser.email.toString())

                fbRef.child(fbUser.uid).setValue(dbUserEntry).addOnCompleteListener {
                //fbRef.setValue(dbUserEntry).addOnCompleteListener {
                    progress.dismiss()
                    if (it.isSuccessful) {
                        fbAuth.signOut()
                        Toast.makeText(this, "Benutzerdaten gespeichert", Toast.LENGTH_LONG).show()
                        progress.dismiss()
                        finish()
                    } else {
                        Snackbar.make(userProfileConstraintLayout, it.exception!!.message.toString(), Snackbar.LENGTH_LONG).show()
                    }
                }
            }else{
                Snackbar.make(userProfileConstraintLayout, it.exception!!.message.toString(), Snackbar.LENGTH_LONG).show()
            }
        }


    }

    private fun showProgressHelper(context: Context, msg: String){
        progress = ProgressDialog(context)
        progress.setMessage(msg)
        progress.setCancelable(false)
        progress.show()
    }

    private fun checkFields(): Boolean {
        if (TextUtils.isEmpty(firstName.text)) {
            firstName.error = "Feld leer"
            return false
        }
        if (TextUtils.isEmpty(lastName.text)) {
            lastName.error = "Feld leer"
            return false
        }
        if(compareFirstName.toString().trim() == firstName.text.toString().trim() && compareLastName.toString().trim() == lastName.text.toString().trim() ) {
            Snackbar.make(userProfileConstraintLayout, "Keine Änderungen vorgenommen, speichern nicht nötig", Snackbar.LENGTH_LONG).show()
            return false
        }
        return true
    }
}
