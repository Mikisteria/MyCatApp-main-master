package com.example.mycatapp

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mycatapp.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class CatAdapter(var items: MutableList<Cat>, context: Context) : RecyclerView.Adapter<CatViewHolder>() {
    private val context = context

    var onItemClick : ((Cat) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cat_item, parent, false)
        return CatViewHolder(view)

    }

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
        holder.name.text = items[position].name


        Log.d(TAG, items[position].image!!.url)

        Glide.with(context)
            .load(items[position].image!!.url)
            .placeholder(com.google.android.material.R.drawable.ic_clock_black_24dp)
            .centerCrop()
            .into(holder.image_url)

        holder.itemView.setOnClickListener{
            onItemClick?.invoke(items[position])
        }

    }




    override fun getItemCount(): Int {
        return items.size
    }

    fun Update(items_new: MutableList<Cat>){
        items = items_new
        this.notifyDataSetChanged()
    }

}