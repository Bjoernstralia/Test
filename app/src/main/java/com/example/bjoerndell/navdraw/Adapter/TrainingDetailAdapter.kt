package com.example.bjoerndell.navdraw.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.bjoerndell.navdraw.Controller.TrainingDetail
import com.example.bjoerndell.navdraw.Model.TrainingType
import com.example.bjoerndell.navdraw.R

class TrainingDetailAdapter (val context: Context, val training: List<TrainingType>, val itemClick: (TrainingType) -> Unit) : RecyclerView.Adapter<TrainingDetailAdapter.TrainingDetailHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrainingDetailHolder {
        val inflatorTraining = LayoutInflater.from(context).inflate(R.layout.training_detail_list_item, parent,false)
        return TrainingDetailHolder(inflatorTraining,itemClick)
    }

    override fun onBindViewHolder(holder: TrainingDetailHolder, position: Int) {
        holder?.bindTraining(context, training[position])
    }

    override fun getItemCount(): Int {
        return training.count()
    }

    inner class TrainingDetailHolder (itemView: View?, val itemClick: (TrainingType) -> Unit ): RecyclerView.ViewHolder(itemView) {

        val trainDescr = itemView?.findViewById<TextView>(R.id.trainingDescr)
        val status = itemView?.findViewById<ImageView>(R.id.trainingStatus)
        val contai = itemView?.findViewById<ImageView>(R.id.trainingRec)

        fun bindTraining (context: Context, training: TrainingType) {

            trainDescr?.text =  training.TrainingTypeName

            itemView.setOnClickListener { itemClick(training)}

        }
    }

}