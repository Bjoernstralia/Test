package com.example.bjoerndell.navdraw.Controller

import android.app.ProgressDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import com.example.bjoerndell.navdraw.R
import com.example.bjoerndell.navdraw.Utilitiy.EXTRA_PASSWORDLENGTH
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var email: EditText
    private lateinit var pin: EditText
    private lateinit var dataCb: CheckBox
    private lateinit var usageCb: CheckBox
    private lateinit var fbAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        email = findViewById(R.id.registerActEmailTxt)
        pin = findViewById(R.id.registerActPinTxt)
        dataCb = findViewById(R.id.registerActDatensCb)
        usageCb = findViewById(R.id.registerActNutzungCb)

        val dataTxt = "${this.getString(R.string.TextDatenschutzbedingungen)} ${this.getString(R.string.Datenschutzbedingungen)}"
        Toast.makeText(this, dataTxt, Toast.LENGTH_LONG).show()

        val usageTxt = "${this.getString(R.string.TextNutzungsbedingungen)} ${this.getString(R.string.Nutzungsbedingungen)}"
        Toast.makeText(this, usageTxt, Toast.LENGTH_LONG).show()

        dataCb.text = dataTxt
        usageCb.text = usageTxt

        registerActRegisterBtn.setOnClickListener{
            registerUser()
        }
    }

    private fun registerUser (){

        if(TextUtils.isEmpty(email.text.toString())){
            email.error = "Gib deine E-Mail-Adresse ein"
            return
        }
        if (TextUtils.isEmpty(pin.text.toString())){
            pin.error = "Gib eine PIN ein"
            return
        } else if (pin.length() < EXTRA_PASSWORDLENGTH){
            pin.error = "Die PIN muss aus mind. $EXTRA_PASSWORDLENGTH Zahlen bestehen"
            return
        }
        if(!usageCb.isChecked){
            usageCb.error = ""
            Toast.makeText(this,getString(R.string.ErrorNutzungsbedingungen), Toast.LENGTH_LONG).show()
            return
        }
        if (!dataCb.isChecked){
            dataCb.error = ""
            Toast.makeText(this,getString(R.string.ErrorDatenschutzbedingungen), Toast.LENGTH_LONG).show()
            return
        }


        val progress = ProgressDialog(this)
        progress.setMessage("Registrierung läuft...")
        progress.setCancelable(false)
        progress.show()

        fbAuth = FirebaseAuth.getInstance()
        fbAuth.createUserWithEmailAndPassword(email.text.toString(), pin.text.toString()).addOnCompleteListener {task: Task<AuthResult> ->
            progress.dismiss()
            if(task.isSuccessful){
                Toast.makeText(this,"Registrierung erfolgreich", Toast.LENGTH_SHORT).show()
                finish()
                val intent = Intent(this, UserProfileActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
            } else {
                val exception = task.exception?.message
                Toast.makeText(this,exception.toString(), Toast.LENGTH_LONG).show()
            }
        }
    }
}
