package com.example.mycatapp.pantallas

import android.content.ContentValues
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mycatapp.R
import com.example.mycatapp.modelo.Cat

class DescriptionCatAdapter(var items: MutableList<Cat>, context: Context) : RecyclerView.Adapter<DescriptionCatViewHolder>() {

    private val context = context
    var onItemClick : ((Cat) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DescriptionCatViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cat_description, parent, false)
        return DescriptionCatViewHolder(view)



    }

    override fun onBindViewHolder(holder: DescriptionCatViewHolder, position: Int) {
        holder.name.text = items[position].name


        Log.d(ContentValues.TAG, items[position].image!!.url!!)

        Glide.with(context)
            .load(items[position].image!!.url)
            .placeholder(com.google.android.material.R.drawable.ic_clock_black_24dp)
            .centerCrop()
            .into(holder.image_url)



    }





    override fun getItemCount(): Int {
        return items.size
    }

    fun Update(items_new: MutableList<Cat>){
        items = items_new
        this.notifyDataSetChanged()
    }
}