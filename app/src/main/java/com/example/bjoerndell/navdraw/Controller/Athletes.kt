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
import com.example.bjoerndell.navdraw.Utilitiy.EXTRA_ATHLETE


class Athletes : AppCompatActivity() {

    lateinit var adapter : AthleteProfileAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_athletes)

        val shortMA = intent.getStringExtra(EXTRA_MARTIALARTTYPE)
        println(shortMA)

        adapter = AthleteProfileAdapter(this, DataService.users) { athlete ->

            val listItem = athlete.name

            Toast.makeText(this, listItem.toString(), Toast.LENGTH_LONG).show()
            val intentTraining = Intent(this, TrainingDetail::class.java)
            intentTraining.putExtra(EXTRA_MARTIALARTTYPE, shortMA)
            intentTraining.putExtra(EXTRA_ATHLETE, listItem)
            startActivity(intentTraining)
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

        val layoutManager = GridLayoutManager(this,spanCount)
        recViewAthletes.layoutManager = layoutManager
        recViewAthletes.adapter = adapter

    }
}
