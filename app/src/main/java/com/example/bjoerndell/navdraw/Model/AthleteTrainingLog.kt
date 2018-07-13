package com.example.bjoerndell.navdraw.Model

import java.time.Duration

class AthleteTrainingLog(val id: String, val athleteId: String, val training: String,
                         val trainingDate: String, val duration: Double, val athleteFName: String,
                         val athleteLName: String) {

    constructor():this("","","","", 0.0, "", "")


}

