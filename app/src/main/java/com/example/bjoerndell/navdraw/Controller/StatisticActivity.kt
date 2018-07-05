package com.example.bjoerndell.navdraw.Controller

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.constraint.solver.widgets.Snapshot
import android.util.Log
import com.example.bjoerndell.navdraw.Model.AthleteTrainingLog
import com.example.bjoerndell.navdraw.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.FirebaseDatabase.*
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat

class StatisticActivity : AppCompatActivity() {

    lateinit var fbAuth: FirebaseAuth
    lateinit var fbDb: FirebaseDatabase

    /*

    1. Anzahl User

     */


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statistic)

        getUserTraining("01.01.2018", "01.01.2019", "BJJ")
    }


    @SuppressLint("SimpleDateFormat")
    fun getUserTraining(dateFrom: String, datUntil: String, maType:String){

        fbAuth = FirebaseAuth.getInstance()
        fbDb = FirebaseDatabase.getInstance()

        val fbRef = fbDb.getReference("TrainingLog")

        //val listStat = mutableListOf<String>()


        val listStat = arrayListOf<String>()
        println("##Start")

        val curFormater = SimpleDateFormat("dd.MM.yyyy")
        val date1 = curFormater.parse(dateFrom)
        val date2 = curFormater.parse(datUntil)

        fbRef.addValueEventListener(object: ValueEventListener{
            override fun onCancelled(dataSnapshot: DatabaseError) {
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {

                for (snapshot in dataSnapshot.children){

                    val trainingLog = snapshot.getValue<AthleteTrainingLog>(AthleteTrainingLog::class.java)
                    val trainingDate = curFormater.parse(trainingLog!!.TrainingDate.toString())

                    println("## $trainingDate")

                    //println(message = "###Before ${trainingLog!!.AthleteId} ${trainingLog.Training} ${trainingLog.TrainingDate}")
                    println("Before## ${trainingLog.AthleteId} ${trainingLog.Training} ${trainingLog.TrainingDate}")

                    //Funktion  getTrainingDetails() mit TrainingID
                    if (trainingDate.after(date1) and trainingDate.before(date2) and maType.equals("BJJ")){
                       println("## ${trainingLog.AthleteId} ${trainingLog.Training} ${trainingLog.TrainingDate}")
                    }

                }

            }
        })


    }
}
