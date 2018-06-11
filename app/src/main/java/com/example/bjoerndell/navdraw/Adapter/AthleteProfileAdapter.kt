package com.example.bjoerndell.navdraw.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.bjoerndell.navdraw.Model.User
import com.example.bjoerndell.navdraw.R
import kotlinx.android.synthetic.main.athlete_list_item.view.*

class AthleteProfileAdapter(val context: Context, val athletes: List<User>, val itemClick: (User)-> Unit) : RecyclerView.Adapter<AthleteProfileAdapter.AthleteProfileHolder>() {


    override fun onBindViewHolder(holder: AthleteProfileHolder, position: Int) {
        holder?.bindAthlete(context, athletes[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AthleteProfileHolder {
       val view = LayoutInflater.from(context).inflate(R.layout.athlete_list_item, parent, false)
        return AthleteProfileHolder(view, itemClick)
    }

    override fun getItemCount(): Int {
        return athletes.count()
    }

    inner class AthleteProfileHolder(itemView: View?, val itemClick: (User) -> Unit) : RecyclerView.ViewHolder(itemView) {
        // wieso hat view? referenz zur athlete_list_item.xml Datei?
        val AthleteImage = itemView?.findViewById<ImageView>(R.id.athletePicture)
        val AthleteName = itemView?.findViewById<TextView>(R.id.athleteName)

        fun bindAthlete (context: Context, athlete: User) {
            val resourceId = context.resources.getIdentifier(athlete.profilePic, "drawable", context.packageName)
            AthleteImage?.setImageResource(resourceId)
            AthleteName?.text = athlete.name //+ " " + athlete.lastName

            itemView.setOnClickListener { itemClick(athlete)}
        }
    }
}