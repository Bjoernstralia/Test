package com.example.bjoerndell.navdraw.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.bjoerndell.navdraw.Model.MartialArtType
import com.example.bjoerndell.navdraw.R


open class MartialArtTypeAdapter(context: Context, martialArtTypes: List<MartialArtType>) : BaseAdapter() {

    val context = context
    val martialArtTypes = martialArtTypes

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val maTypeView: View

        maTypeView = LayoutInflater.from(context).inflate(R.layout.martial_art_type_list_item, null)

        val typeName: TextView = maTypeView.findViewById(R.id.martialArtTypeImageText)
        val typeImage: ImageView = maTypeView.findViewById(R.id.martialArtTypeImage)

        val type = martialArtTypes[position]

        val resourceId = context.resources.getIdentifier(type.image, "drawable",context.packageName)

        typeImage.setImageResource(resourceId)
        typeName.text = type.nameType

        return maTypeView
    }


    override fun getItem(position: Int): Any {
        return martialArtTypes[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return martialArtTypes.count()
    }


}
