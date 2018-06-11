package com.example.bjoerndell.navdraw.Services

import com.example.bjoerndell.navdraw.Model.MartialArtType
import com.example.bjoerndell.navdraw.Model.TrainingType
import com.example.bjoerndell.navdraw.Model.User

object DataService {

    val martialarts = listOf(
            MartialArtType("Brazilian Jiu Jitsu","Grappling", "bw_bjj", "BJJ"),
            MartialArtType("Wing Tsun","Self-defense", "yipman_brucelee","WT"),
            MartialArtType("Kickboxing","Full-contact", "bw_kickboxing", "KB")
    )

    val bjj = listOf(
            TrainingType("BJJ-Drills", 2.0),
            TrainingType("Open Mat", 2.0),
            TrainingType("BJJ-Basics", 1.0),
            TrainingType("BJJ-Team", 1.5),
            TrainingType("NoGi", 1.0)
    )

    val wingtsun = listOf(
            TrainingType("WT-Jugendliche", 1.0),
            TrainingType("WT-Erwachsene", 1.5),
            TrainingType("Selbstverteidigung-Kinder", 1.0)
    )

    val kickboxing = listOf(
            TrainingType("Kickboxing", 1.0)
    )



    val users = listOf(
            User("Björn","Viehmann", "me"),
            User("Serkan", "Ortac","placeholder_person"),
            User("Dennis","Viehmann","placeholder_person"),
            User("Dima", "Ortac","placeholder_person"),
            User("Murat","Viehmann","placeholder_person"),
            User("Arjan", "Ortac","placeholder_person"),
            User("Benny","Viehmann","placeholder_person"),
            User("Michele", "Ortac","placeholder_person"),
            User("User1","Viehmann","placeholder_person"),
            User("User2", "Ortac","placeholder_person"),
            User("User3","Viehmann","placeholder_person"),
            User("User4", "Ortac","placeholder_person"),
            User("User5","Viehmann","placeholder_person")
    )

}
