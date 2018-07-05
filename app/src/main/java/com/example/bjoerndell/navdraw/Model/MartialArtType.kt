package com.example.bjoerndell.navdraw.Model

import android.media.Image

class MartialArtType (val nameType: String, val category: String, val image: String, val NameTypeShort: String,
                      val id: String) {
    override fun toString(): String {
        return nameType
    }
}