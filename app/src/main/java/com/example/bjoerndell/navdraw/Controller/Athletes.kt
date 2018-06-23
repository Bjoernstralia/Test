package com.example.bjoerndell.navdraw.Controller

import android.content.Intent
import android.content.res.Configuration
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.widget.GridLayout
import android.widget.Toast
import com.example.bjoerndell.navdraw.Adapter.AthleteProfileAdapter
import com.example.bjoerndell.navdraw.R
import com.example.bjoerndell.navdraw.R.id.recViewAthletes
import com.example.bjoerndell.navdraw.Services.DataService
import com.example.bjoerndell.navdraw.Utilitiy.EXTRA_MARTIALARTTYPE
import kotlinx.android.synthetic.main.activity_athletes.*
import android.view.Gravity
import android.widget.PopupWindow
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.view.LayoutInflater
import android.widget.ListView
import com.example.bjoerndell.navdraw.Model.User
import com.example.bjoerndell.navdraw.Utilitiy.EXTRA_ATHLETE
import com.google.firebase.database.*


class Athletes : AppCompatActivity() {

    lateinit var adapter : AthleteProfileAdapter


    //
    var mInst = FirebaseDatabase.getInstance()
    var myRef = mInst.reference.child("Users")
    lateinit var mUserList: MutableList<User>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_athletes)

        val shortMA = intent.getStringExtra(EXTRA_MARTIALARTTYPE)
        //println(shortMA)


        //getting USERS here

        mUserList = mutableListOf()
        println("#Loggin: Start")
        /*
        Problem mit den Leseberechtigungen auf der Datenbank. Man benötigt einen aktiven User, um die DB lesen zu können.
        allerdings soll zu diesem Zeitpunkt noch kein User eingelogt sein. Ich benötige in diesem Screen alle User.
        Kann man für die App einen Admin Account oder ShadowUser erstellen?
         */

        myRef.addValueEventListener(object: ValueEventListener{
            override fun onCancelled(p0: DatabaseError?) {
            }
            override fun onDataChange(p0: DataSnapshot?) {
                println("#Loggin: onDataChange")
                mUserList.clear()
                if (p0!!.exists()){
                    for(h in p0.children){
                        val user = h.getValue(User::class.java)
                        mUserList.add(user!!)
                        println("#Logging: $user!!")
                    }
                }
            }
        })
        println("#Loggin: creating List successful")
        //end getting USERS


        //println("#Logging: nix")

        adapter = AthleteProfileAdapter(this, DataService.users) { athlete ->
        //adapter = AthleteProfileAdapter(this, mUserList) { athlete ->

            val listItem = athlete.name
            Toast.makeText(this, listItem.toString(), Toast.LENGTH_LONG).show()
            val intentTraining = Intent(this, TrainingDetail::class.java)
            intentTraining.putExtra(EXTRA_MARTIALARTTYPE, shortMA)
            intentTraining.putExtra(EXTRA_ATHLETE, listItem)
            startActivity(intentTraining)
        }
        println("#Loggin: lambda adapter successful")

        var spanCount = 2
        val orientation = resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            spanCount = 3
        }

        val screensize = resources.configuration.screenWidthDp
        if (screensize > 720) {
            spanCount = 4
        }
        println("#Loggin: spanCounts successful")

        val layoutManager = GridLayoutManager(this,spanCount)
        recViewAthletes.layoutManager = layoutManager
        recViewAthletes.adapter = adapter

    }
}
