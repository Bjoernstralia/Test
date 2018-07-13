package com.example.bjoerndell.navdraw.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.bjoerndell.navdraw.Model.AthleteTrainingLog
import com.example.bjoerndell.navdraw.Model.User
import com.example.bjoerndell.navdraw.R
import kotlinx.android.synthetic.main.athlete_list_item.view.*

class AthleteTrainingLogAdapter(val context: Context, val logs: MutableList<AthleteTrainingLog>, val itemClick: (AthleteTrainingLog)-> Unit) : RecyclerView.Adapter<AthleteTrainingLogAdapter.AthleteProfileHolder>() {


    override fun onBindViewHolder(holder: AthleteProfileHolder, position: Int) {
        holder?.bindStatistic(context, logs[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AthleteProfileHolder {
       val view = LayoutInflater.from(context).inflate(R.layout.athlete_list_item, parent, false)
        return AthleteProfileHolder(view, itemClick)
    }

    override fun getItemCount(): Int {
        return logs.count()
    }

    inner class AthleteProfileHolder(itemView: View?, val itemClick: (AthleteTrainingLog) -> Unit) : RecyclerView.ViewHolder(itemView) {

        //val AthleteImage = itemView?.findViewById<ImageView>(R.id.athleteStatPicture)
        val AthleteName = itemView?.findViewById<TextView>(R.id.athleteStatFName)

        fun bindStatistic (context: Context, stat: AthleteTrainingLog) {
            //val resourceId = context.resources.getIdentifier(athlete.profilePic, "drawable", context.packageName)
            //AthleteImage?.setImageResource(resourceId)
            AthleteName?.text = stat.athleteId

            itemView.setOnClickListener { itemClick(stat)}
        }
    }
}