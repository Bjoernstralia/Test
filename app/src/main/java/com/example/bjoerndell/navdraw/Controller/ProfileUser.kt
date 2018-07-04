package com.example.bjoerndell.navdraw.Controller

import android.app.ProgressDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.bjoerndell.navdraw.Model.User
import com.example.bjoerndell.navdraw.R
import com.example.bjoerndell.navdraw.Utilitiy.CHOOSE_IMAGE
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_create_user.*
import kotlinx.android.synthetic.main.activity_profile_user.*
import android.provider.MediaStore
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.os.storage.StorageManager
import com.example.bjoerndell.navdraw.R.id.profilePictureImg
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import java.io.ByteArrayOutputStream
import java.io.File


class ProfileUser : AppCompatActivity() {


    lateinit var mAuth: FirebaseAuth
    lateinit var fbStorage: FirebaseStorage
    lateinit var fbRef: StorageReference

    lateinit var txtVorname: TextView
    lateinit var txtNachname: TextView


    val REQUEST_IMAGE_CAPTURE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_user)

        mAuth = FirebaseAuth.getInstance()
        fbStorage = FirebaseStorage.getInstance()


        txtVorname = findViewById(R.id.profileActFnameTxt)
        txtNachname = findViewById(R.id.profileActLnameTxt)

        val user = mAuth.currentUser
        if (user != null) {
        }

        profileActUpdateBtn.setOnClickListener() {
            updateUserInformation()
        }

        profilePictureImg.setOnClickListener() {
            dispatchTakePictureIntent()
        }
    }

    private fun updateUserInformation() {

        val user = mAuth.currentUser
        val vorname = profileActFnameTxt.text.toString().trim()
        val nachname = profileActLnameTxt.text.toString().trim()

        if (user == null) {
            Toast.makeText(this, "Es ist kein Benutzer eingeloggt", Toast.LENGTH_LONG).show()
            return
        }
        if (TextUtils.isEmpty(vorname)) {
            txtVorname.error = "Feld leer"
            return
        }
        if (TextUtils.isEmpty(nachname)) {
            txtNachname.error = "Feld leer"
            return
        }

        val progress = ProgressDialog(this)
        progress.setMessage("Profil wird erweitert...")
        progress.setCancelable(false)
        progress.show()


        val newUser = User(vorname, nachname, "placeholder_person", user.uid, user.email.toString())
        val fbDataBase = FirebaseDatabase.getInstance().getReference("Users")
        val userProfileUpdate = UserProfileChangeRequest.Builder()
                .setDisplayName("$vorname $nachname")
                .build()

        mAuth.currentUser!!.updateProfile(userProfileUpdate).addOnSuccessListener {

            fbDataBase.child(user!!.uid).setValue(newUser).addOnCompleteListener() {
                progress.dismiss()
                if (it.isSuccessful) {
                    Toast.makeText(this, "Profil aktualisiert", Toast.LENGTH_LONG).show()
                    mAuth.signOut()
                    finish()

                } else {
                    Toast.makeText(this, it.exception?.message.toString(), Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)

        try {
            if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

                val extras = data.extras;
                val imageBitmap = extras.get("data") as Bitmap
                profilePictureImg.setImageBitmap(imageBitmap)

                fbRef = fbStorage.getReference()

                val newRef = fbRef.child("Users.jpg")
                val newRefA = fbRef.child("Users/Users.jpg")

                // While the file names are the same, the references point to different files
                newRef.getName().equals(newRefA.getName());    // true
                newRef.getPath().equals(newRefA.getPath());    // false


                // Get the data from an ImageView as bytes
                profilePictureImg.setDrawingCacheEnabled(true);
                profilePictureImg.buildDrawingCache();
                val bitmap = profilePictureImg.getDrawingCache();

                val baos = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                val data = baos.toByteArray()
                //ByteArrayOutputStream baos = new ByteArrayOutputStream();

                val upload = newRef.putBytes(data)
                upload.addOnCompleteListener{
                    if(it.isSuccessful){
                        val uriUrl = it.result.downloadUrl
                        Toast.makeText(this, uriUrl.toString(), Toast.LENGTH_LONG).show()
                    }else{
                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
            catch (e: Exception) {
                Toast.makeText(this, e.message.toString(), Toast.LENGTH_LONG).show()
            }
    }
}




