package com.example.bjoerndell.navdraw.Controller

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.TextView
import com.example.bjoerndell.navdraw.Model.AthleteTrainingLog
import com.example.bjoerndell.navdraw.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.util.*

class StatisticActivity : AppCompatActivity() {

    lateinit var fbAuth: FirebaseAuth
    lateinit var fbDb: FirebaseDatabase
    lateinit var fbDbInst: DatabaseReference

    val dateFormatPattern = "dd.MM.yyyy"
    val curFormatter = SimpleDateFormat(dateFormatPattern, Locale.GERMANY)

    lateinit var dateUntil: TextView
    lateinit var dateFrom: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statistic)

        fbAuth = FirebaseAuth.getInstance()
        fbDb = FirebaseDatabase.getInstance()

        val dateFrom = findViewById<TextView>(R.id.statisticActDateFrom)
        val dateUntil = findViewById<TextView>(R.id.statisticActDateUntil)
        val filterBtn = findViewById<Button>(R.id.statisticActFilterBtn)

        //textView.text = SimpleDateFormat("dd.MM.yyyy").format(System.currentTimeMillis())

        var cal = Calendar.getInstance()

        val a = "01.01.${cal.get(Calendar.YEAR)-1}"
        val c = curFormatter.parse(a)
        println("+++$c")
        dateFrom.text = curFormatter.format(c)
        dateUntil.text = curFormatter.format(cal.time)

        val dateFromPickerListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            dateFrom.text = curFormatter.format(cal.time)
        }
        val dateUntilPickerListener = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            dateUntil.text = curFormatter.format(cal.time)
        }
        dateFrom.setOnClickListener {
            DatePickerDialog(this@StatisticActivity, dateFromPickerListener,
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)).show()
        }
        dateUntil.setOnClickListener{
            DatePickerDialog(this@StatisticActivity, dateUntilPickerListener,
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)
                    ).show()
        }

        filterBtn.setOnClickListener{
            getUserTraining(dateFrom.text.toString(), dateUntil.text.toString())
        }
    }





    @SuppressLint("SimpleDateFormat")
    private fun getUserTraining(from: String, until: String){

        if (TextUtils.isEmpty(from)){
            dateFrom.error=""
            return
        }
        if(TextUtils.isEmpty(until)){
            dateUntil.error=""
            return
        }

        fbDbInst = fbDb.getReference("TrainingLog")

        val listStat = mutableListOf<AthleteTrainingLog>()
        val date1 = curFormatter.parse(from)
        val date2 = curFormatter.parse(until)

        fbDbInst.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()){
                    for(element in dataSnapshot.children){
                        val obj = element.getValue<AthleteTrainingLog>(AthleteTrainingLog::class.java)
                        val trainingDate = curFormatter.parse(obj!!.trainingDate)

                        println("$trainingDate after $date1 and $trainingDate before $date2")
                        if(trainingDate.after(date1) && trainingDate<=(date2)){
                            println("+++1 Datum innerhalb Filter= $trainingDate ${obj.id}")
                            listStat.add(obj)
                        }
                    }
                }
                val amount = findViewById<TextView>(R.id.statisticActAmountTxt)
                amount.text = "Anzahl der gesamten Trainings = ${listStat.count().toString()}"

            }
            override fun onCancelled(databaseError: DatabaseError) {
            }
        })


    }
}
