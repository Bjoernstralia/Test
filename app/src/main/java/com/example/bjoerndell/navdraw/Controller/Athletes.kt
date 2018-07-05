package com.example.bjoerndell.navdraw.Controller

import android.app.ProgressDialog
import android.content.Intent
import android.content.res.Configuration
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import com.example.bjoerndell.navdraw.Adapter.AthleteProfileAdapter
import com.example.bjoerndell.navdraw.R
import com.example.bjoerndell.navdraw.R.id.recViewAthletes
import com.example.bjoerndell.navdraw.Services.DataService
import com.example.bjoerndell.navdraw.Utilitiy.EXTRA_MARTIALARTTYPE
import kotlinx.android.synthetic.main.activity_athletes.*
import android.view.Gravity
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.view.LayoutInflater
import com.example.bjoerndell.navdraw.Model.User
import com.example.bjoerndell.navdraw.Utilitiy.EXTRA_ATHLETE
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import android.content.DialogInterface
import android.support.v7.app.AlertDialog
import android.text.InputType
import android.text.TextUtils
import android.util.Log
import android.widget.*
import com.example.bjoerndell.navdraw.Utilitiy.EXTRA_PASSWORDLENGTH
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import java.util.concurrent.CountDownLatch
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DatabaseReference
import com.google.android.gms.tasks.TaskCompletionSource
import com.google.android.gms.tasks.Tasks


class Athletes : AppCompatActivity() {

    lateinit var adapter : AthleteProfileAdapter

    lateinit var fbAuth: FirebaseAuth
    lateinit var fbDb: FirebaseDatabase
    lateinit var fbDbInst: DatabaseReference
    lateinit var mUserList: MutableList<User>


    private var mText = ""
    lateinit var shortMA: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_athletes)

        shortMA = intent.getStringExtra(EXTRA_MARTIALARTTYPE)


        mUserList = mutableListOf()
        //mUserList.addAll(DataService.users)

        fbAuth = FirebaseAuth.getInstance()
        fbDb = FirebaseDatabase.getInstance()
        fbDbInst = fbDb.getReference("Users")

        if (fbAuth.currentUser == null){
            //Toast.makeText(this,"Kein User eingeloggt", Toast.LENGTH_LONG).show()
            fbAuth.signInAnonymously()
        }

        getUserList()
        /*
        fbDbInst.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()){
                    for(element in dataSnapshot.children){
                        val user = element.getValue<User>(User::class.java)
                        mUserList.add(user!!)
                    }
                    setAdapter(mUserList)
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
            }
        })
        */

    }

    fun getUserList(){
        fbAuth = FirebaseAuth.getInstance()
        fbDb = FirebaseDatabase.getInstance()
        fbDbInst = fbDb.getReference("Users")

        fbDbInst.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()){
                    for(element in dataSnapshot.children){
                        val user = element.getValue<User>(User::class.java)
                        mUserList.add(user!!)
                    }
                    setAdapter(mUserList)
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
            }
        })
    }

    fun setAdapter(list: MutableList<User>){

        adapter = AthleteProfileAdapter(this, list) { athlete ->
            //login user
            showLoginDialog(athlete.email, shortMA)
        }

        var spanCount = 2
        val orientation = resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            spanCount = 3
        }
        val screensize = resources.configuration.screenWidthDp
        if (screensize > 720) {
            spanCount = 4
        }
        val layoutManager = GridLayoutManager(this, spanCount)
        recViewAthletes.layoutManager = layoutManager
        recViewAthletes.adapter = adapter
    }

    fun showLoginDialog(email: String, shortMA: String) {

        val custBuilder = AlertDialog.Builder(this)
        val custView = LayoutInflater.from(this).inflate(R.layout.dialog_login, null)

        //val custTitle = custView.findViewById<TextView>(R.id.dialogLoginTitleTxt)
        val custPin = custView.findViewById<EditText>(R.id.dialogLoginPinTxt)
        val custBtn = custView.findViewById<Button>(R.id.dialogLoginLoginBtn)

        custBtn.setOnClickListener(){
            if (TextUtils.isEmpty(custPin.text.toString())){
                custPin.error = "Gib eine PIN ein"
            } else if (custPin.length() < EXTRA_PASSWORDLENGTH){
                custPin.error = "Die PIN muss aus mind. $EXTRA_PASSWORDLENGTH Zahlen bestehen"
            } else
            {
                mText = custPin.text.toString()
                if((mText.isEmpty()) or (mText == "")) {
                    Toast.makeText(this,"Bitte gib die PIN ein", Toast.LENGTH_LONG).show()
                } else {
                    loginAthlete(email, mText)
                }
            }
        }
        custBuilder.setView(custView)
        custBuilder.show()
    }

    fun loginAthlete(name:String, pin:String){

        val progress = ProgressDialog(this)
        progress.setMessage("einloggen...")
        progress.setCancelable(false)
        progress.show()

        fbAuth = FirebaseAuth.getInstance()
        fbAuth.signInWithEmailAndPassword(name, pin).addOnCompleteListener {task: Task<AuthResult> ->
            progress.dismiss()
            if(task.isSuccessful){

                val profileIntent = Intent(this,UserProfileActivity::class.java)
                //profileIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                finish()
                startActivity(profileIntent)

                val listItem = name
                val intentTraining = Intent(this, TrainingDetail::class.java)
                intentTraining.putExtra(EXTRA_MARTIALARTTYPE, shortMA)
                intentTraining.putExtra(EXTRA_ATHLETE, listItem)
                startActivity(intentTraining)
            }else {
                val exception = task.exception?.message
                Toast.makeText(this,exception.toString(), Toast.LENGTH_LONG).show()
            }
        }




    }
}
