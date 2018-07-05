package com.example.bjoerndell.navdraw.Controller

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import com.example.bjoerndell.navdraw.Adapter.TrainingDetailAdapter
import com.example.bjoerndell.navdraw.R
import com.example.bjoerndell.navdraw.Services.DataService
import com.example.bjoerndell.navdraw.Utilitiy.EXTRA_MARTIALARTTYPE
import kotlinx.android.synthetic.main.activity_training_detail.*
import android.widget.Toast
import com.example.bjoerndell.navdraw.Model.AthleteTrainingLog
import com.example.bjoerndell.navdraw.Model.TrainingType
import com.example.bjoerndell.navdraw.Utilitiy.EXTRA_ATHLETE
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.*


class TrainingDetail : AppCompatActivity() {

    lateinit var adapter: TrainingDetailAdapter
    lateinit var mAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_training_detail)

        val shortMA = intent.getStringExtra(EXTRA_MARTIALARTTYPE)
        val athlete = intent.getStringExtra(EXTRA_ATHLETE)

        var serviceData: List<TrainingType>


        when(shortMA){
            "BJJ" -> adapter = TrainingDetailAdapter(this, DataService.bjj)
            "WT" -> adapter = TrainingDetailAdapter(this, DataService.wingtsun)
            "KB" -> adapter = TrainingDetailAdapter(this, DataService.kickboxing)
        }
        //adapter = TrainingDetailAdapter(this, DataService.bjj)

        var spanCount: Int
        val displaySize = resources.configuration.screenWidthDp

        spanCount = when {
            displaySize < 320 -> 1
            displaySize in 601..719 -> 3
            displaySize > 720 -> 4
            else -> 2
        }

        //320dp: a typical phone screen (240x320 ldpi, 320x480 mdpi, 480x800 hdpi, etc).
        //480dp: a tweener tablet like the Streak (480x800 mdpi).
        //600dp: a 7” tablet (600x1024 mdpi).
        //720dp: a 10” tablet (720x1280 mdpi, 800x1280 mdpi, etc).

        val layoutTraining = GridLayoutManager(this, spanCount)
        trainingdetRecView.layoutManager = layoutTraining
        trainingdetRecView.adapter = adapter

        btnStartTraining.setOnClickListener() {
            WriteData(athlete)
        }
    }

    fun ToastDummy(context: Context, msg: String = "Test") {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    fun WriteData(athlete: String) {

        mAuth = FirebaseAuth.getInstance()

        val user = mAuth.currentUser
        if (user ==null){
            Toast.makeText(this,"Kein Benutzer angemeldet", Toast.LENGTH_LONG).show()
            return
        }

            val calendar: Calendar = Calendar.getInstance()
            val currentDate = SimpleDateFormat("dd.MM.yyyy").format(calendar.time)
            val selectedTraining = adapter.getTrueValues()
            var i = 0

            val fb = FirebaseDatabase.getInstance().getReference("TrainingLog")

            while (i != selectedTraining.count()) {
                var selectedItem = selectedTraining[i].TrainingTypeName
                var duration = selectedTraining[i].Duration
                println(selectedItem)

                var trainingId = fb.push().key
                var detail = AthleteTrainingLog(trainingId, user!!.uid, selectedItem,currentDate)

                //Mit oder ohne .child?
                //fb.setValue(trainingDetail)
                fb.child(trainingId).setValue(detail).addOnCompleteListener() {
                    if (it.isSuccessful){
                        Toast.makeText(applicationContext, "Training wurde angelegt!", Toast.LENGTH_SHORT).show()
                        mAuth.signOut()
                        finish()
                    }
                    else {
                        Toast.makeText(applicationContext, it.exception?.message, Toast.LENGTH_SHORT).show()
                    }
                }
                i++
            }
        }
}
