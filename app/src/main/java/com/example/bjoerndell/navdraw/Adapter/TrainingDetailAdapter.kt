package com.example.bjoerndell.navdraw.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.bjoerndell.navdraw.Model.TrainingType
import com.example.bjoerndell.navdraw.R

//class TrainingDetailAdapter (val context: Context, val training: List<TrainingType>, val itemClick: (TrainingType) -> Unit) : RecyclerView.Adapter<TrainingDetailAdapter.TrainingDetailHolder>(){

class TrainingDetailAdapter (val context: Context, val training: List<TrainingType>) : RecyclerView.Adapter<TrainingDetailAdapter.TrainingDetailHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrainingDetailHolder {
        val inflatorTraining = LayoutInflater.from(context).inflate(R.layout.training_detail_list_item, parent,false)
        //return TrainingDetailHolder(inflatorTraining,itemClick)
        return TrainingDetailHolder(inflatorTraining)
    }

    override fun onBindViewHolder(holder: TrainingDetailHolder, position: Int) {
        holder?.bindTraining(context, training[position])
    }

    override fun getItemCount(): Int {
        //training.filter { it.Status }
        return training.count()
    }

    fun getTrueValues(): List<TrainingType> {
        return training.filter { it.Status }
    }

    //inner class TrainingDetailHolder (itemView: View?, val itemClick: (TrainingType) -> Unit ): RecyclerView.ViewHolder(itemView) {
    inner class TrainingDetailHolder (itemView: View?): RecyclerView.ViewHolder(itemView) {

        val trainDescr = itemView?.findViewById<TextView>(R.id.trainingDescr)
        val status: ImageView? = itemView?.findViewById<ImageView>(R.id.trainingStatus)

        val contai = itemView?.findViewById<ImageView>(R.id.trainingRec)


        fun bindTraining (context: Context, training: TrainingType) {

            trainDescr?.text =  training.TrainingTypeName

            setBackgroundResourceRecycle(training.Status)

            itemView.setOnClickListener(View.OnClickListener {
                training.Status = !training.Status
                setBackgroundResourceRecycle(training.Status)
            })
        }

        fun setBackgroundResourceRecycle(statusaa: Boolean) {
            if (statusaa) {
                status?.setBackgroundResource(R.drawable.ic_cb_true)
            } else {
                status?.setBackgroundResource(R.drawable.ic_cb_false)
            }
        }
    }
}