package com.example.bjoerndell.navdraw.Controller

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import com.example.bjoerndell.navdraw.Adapter.TrainingDetailAdapter
import com.example.bjoerndell.navdraw.R
import com.example.bjoerndell.navdraw.R.id.imageView
import com.example.bjoerndell.navdraw.Services.DataService
import com.example.bjoerndell.navdraw.Utilitiy.EXTRA_MARTIALARTTYPE
import kotlinx.android.synthetic.main.activity_training_detail.*
import kotlinx.android.synthetic.main.training_detail_list_item.*
import android.widget.Toast
import android.widget.ImageButton
import android.widget.ImageView


class TrainingDetail : AppCompatActivity() {

    lateinit var adapter : TrainingDetailAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_training_detail)

        val shortMA = intent.getStringExtra(EXTRA_MARTIALARTTYPE)

        adapter = TrainingDetailAdapter(this, DataService.bjj) {moveTrain ->

            Toast.makeText(this, "Clicked on ${moveTrain.TrainingTypeName}", Toast.LENGTH_SHORT)
        }

        val layoutTraining = GridLayoutManager(this, 3)
        trainingdetRecView.layoutManager = layoutTraining
        trainingdetRecView.adapter = adapter
    }
}
