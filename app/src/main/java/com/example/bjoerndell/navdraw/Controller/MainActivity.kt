package com.example.bjoerndell.navdraw.Controller

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import com.example.bjoerndell.navdraw.Adapter.MartialArtTypeAdapter
import com.example.bjoerndell.navdraw.R
import com.example.bjoerndell.navdraw.Services.DataService
import com.example.bjoerndell.navdraw.Utilitiy.EXTRA_MARTIALARTTYPE
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import android.view.Gravity
import android.widget.PopupWindow
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.view.LayoutInflater
import com.google.firebase.auth.FirebaseAuth
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var adapter: MartialArtTypeAdapter

    override fun onDestroy() {
        super.onDestroy()

        val auth = FirebaseAuth.getInstance()
        if (auth.currentUser != null){
            auth.signOut()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)


        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)


        // Start Dummy Daten
        adapter = MartialArtTypeAdapter(this, DataService.martialarts)
        contentListViewMain.adapter = adapter
        // Ende

        contentListViewMain.setOnItemClickListener { parent: AdapterView<*>?, view: View?, position: Int, id: Long ->
            val listItem = DataService.martialarts[position]
            //Toast.makeText(this , "clicken on ${listItem.nameType} cell", Toast.LENGTH_SHORT).show()

            val intentAthletes = Intent(this, Athletes::class.java)
            intentAthletes.putExtra(EXTRA_MARTIALARTTYPE, listItem.NameTypeShort)
            startActivity(intentAthletes)
        }


    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_start_training -> {
                // Handle the camera action
            }
            R.id.nav_create_user -> {
                val intent = Intent(this, CreateUser::class.java)
                startActivity(intent)

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
