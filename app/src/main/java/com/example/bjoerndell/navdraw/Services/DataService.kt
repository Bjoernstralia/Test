package com.example.bjoerndell.navdraw.Services

import com.example.bjoerndell.navdraw.Model.MartialArtType
import com.example.bjoerndell.navdraw.Model.TrainingType
import com.example.bjoerndell.navdraw.Model.User
import com.google.firebase.database.FirebaseDatabase

object DataService {

    val martialarts = listOf(
            MartialArtType("Brazilian Jiu Jitsu","Grappling", "bw_bjj", "BJJ"),
            MartialArtType("Wing Tsun","Self-defense", "yipman_brucelee","WT"),
            MartialArtType("Kickboxing","Full-contact", "bw_kickboxing", "KB")
    )

    val bjj = listOf(
            TrainingType("BJJ Drills", 2.0, false),
            TrainingType("Open Mat", 2.0, false),
            TrainingType("BJJ Basics", 1.0, false),
            TrainingType("BJJ Team", 1.5, false),
            TrainingType("BJJ NoGi", 1.0, true)
    )

    val wingtsun = listOf(
            TrainingType("WT-Jugendliche", 1.0),
            TrainingType("WT-Erwachsene", 1.5),
            TrainingType("Selbstverteidigung-Kinder", 1.0)
    )

    val kickboxing = listOf(
            TrainingType("Kickboxing", 1.0)
    )

    val users = mutableListOf(
            User("Björn","Viehmann", "me","1"),
            User("Serkan", "Ortac","placeholder_person","2"),
            User("Dennis","Viehmann","placeholder_person","3"),
            User("Dima", "Ortac","placeholder_person","4"),
            User("Murat","Viehmann","placeholder_person","5"),
            User("Arjan", "Ortac","placeholder_person","6"),
            User("Benny","Viehmann","placeholder_person","7"),
            User("Michele", "Ortac","placeholder_person","8"),
            User("User1","Viehmann","placeholder_person","9"),
            User("User2", "Ortac","placeholder_person","10"),
            User("User3","Viehmann","placeholder_person","11"),
            User("User4", "Ortac","placeholder_person","12"),
            User("User5","Viehmann","placeholder_person","13")
    )




}
