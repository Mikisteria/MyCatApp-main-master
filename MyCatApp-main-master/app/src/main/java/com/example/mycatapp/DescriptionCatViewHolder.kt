package com.example.mycatapp

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DescriptionCatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val name : TextView = itemView.findViewById(R.id.lblName)
    val temperament : TextView = itemView.findViewById(R.id.lblTemperament)
    val origin : TextView = itemView.findViewById(R.id.lblOrigin)
    val description : TextView = itemView.findViewById(R.id.lblDescription)
    val image_url: ImageView = itemView.findViewById(R.id.imageCat)

    }
