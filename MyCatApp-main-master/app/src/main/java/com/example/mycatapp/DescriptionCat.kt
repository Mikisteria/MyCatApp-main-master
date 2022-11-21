package com.example.mycatapp

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class DescriptionCat : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cat_description)

        val btnBack : Button = findViewById(R.id.buttonBack)
        val cat = intent.getParcelableExtra<Cat>("cat")
        if(cat != null)
        {
            Log.d("que", "onda")
            val imageView : ImageView = findViewById(R.id.imageCat)
            val textView : TextView = findViewById(R.id.lblOrigin)
            val textView2 : TextView = findViewById(R.id.lblDescription)
            val textView3 : TextView = findViewById(R.id.lblTemperament)
            Log.d("que", "ondax2")
            Log.d("que", cat.image!!.url)
            textView.text = cat.origin
            textView2.text = cat.description
            textView3.text = cat.temperament

            Glide.with(this)
                .load(cat.image!!.url)
                .placeholder(com.google.android.material.R.drawable.ic_clock_black_24dp)
                .centerCrop()
                .into(imageView)

            Log.d("que", "ondax3")


        }
        btnBack.setOnClickListener{
            startActivity(Intent(this@DescriptionCat, MainActivity::class.java))
        }
        }
}

/*
class DescriptionCat (var items: MutableList<Cat>, context: Context) : RecyclerView.Adapter<DescriptionCatViewHolder>() {
private val context = context

override fun onCreate(savedInstanceState : Bundle?){
    super.onC

}


override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DescriptionCatViewHolder {
    val view = LayoutInflater.from(parent.context).inflate(R.layout.cat_description, parent, false)
    return DescriptionCatViewHolder(view)


}

override fun onBindViewHolder(holder: DescriptionCatViewHolder, position: Int) {
    holder.name.text = items[position].name
    holder.temperament.text = items[position].temperament
    holder.origin.text = items[position].origin
    holder.description.text = items[position].description
    Log.d(ContentValues.TAG, items[position].image.url)

    Glide.with(context)
        .load(items[position].image.url)
        .placeholder(com.google.android.material.R.drawable.ic_clock_black_24dp)
        .centerCrop()
        .into(holder.image_url)


}


override fun getItemCount(): Int {
    return items.size
}


}
*/