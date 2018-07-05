package com.example.bjoerndell.navdraw.Services

import com.example.bjoerndell.navdraw.Model.MartialArtType
import com.example.bjoerndell.navdraw.Model.TrainingType
import com.example.bjoerndell.navdraw.Model.User
import com.google.firebase.database.FirebaseDatabase

object DataService {

    val martialarts = listOf(
            MartialArtType("Brazilian Jiu Jitsu","Grappling", "bw_bjj", "BJJ","a"),
            MartialArtType("Wing Tsun","Self-defense", "yipman_brucelee","WT","b"),
            MartialArtType("Kickboxing","Full-contact", "bw_kickboxing", "KB","c")
    )

    val bjj = listOf(
            TrainingType("BJJ Drills", 2.0, false, "1","BJJ"),
            TrainingType("Open Mat", 2.0, false,"2","BJJ"),
            TrainingType("BJJ Basics", 1.0, false,"3","BJJ"),
            TrainingType("BJJ Team", 1.5, false,"4","BJJ"),
            TrainingType("BJJ NoGi", 1.0, false,"5","BJJ")
    )

    val wingtsun = listOf(
            TrainingType("WT-Jugendliche", 1.0,false,MATypeId = "WT"),
            TrainingType("WT-Erwachsene", 1.5,false,MATypeId = "WT"),
            TrainingType("Selbstverteidigung-Kinder", 1.0, false, MATypeId = "WT")
    )

    val kickboxing = listOf(
            TrainingType("Kickboxing", 1.0, false, MATypeId = "KB")
    )

    val users = mutableListOf(
            User("Björn","Viehmann", "me","6lbLOhwR3zQmIg1NSSAWJX8dVhC2", "bv@fino.team"),
            User("Test", "Account","placeholder_person","2","a@a.team")
    )




}
