package com.example.mycatapp.pantallas

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mycatapp.R
import org.w3c.dom.Text

class CatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val name : TextView = itemView.findViewById(R.id.lblName)

    val image_url: ImageView = itemView.findViewById(R.id.imageCat)

}