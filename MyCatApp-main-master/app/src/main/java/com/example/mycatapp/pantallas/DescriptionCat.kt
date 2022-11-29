package com.example.mycatapp.pantallas

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mycatapp.R
import com.example.mycatapp.data.ApiService
import com.example.mycatapp.data.MainRepository
import com.example.mycatapp.modelo.Cat

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext


class DescriptionCat : AppCompatActivity(){

    private val coroutineContext: CoroutineContext = newSingleThreadContext("Descripcion")
    private val scope = CoroutineScope(coroutineContext)
    private lateinit var cat : Cat

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cat_description)

        val btnBack : Button = findViewById(R.id.buttonBack)

        cat = intent.getParcelableExtra<Cat>("cat")!!
        if(cat != null)
        {
            Log.d("que", "onda")
            val imageView : ImageView = findViewById(R.id.imageCat)
            val textView : TextView = findViewById(R.id.lblOrigin)
            val textView2 : TextView = findViewById(R.id.lblDescription)
            val textView3 : TextView = findViewById(R.id.lblTemperament)
            Log.d("que", "ondax2")
            Log.d("que", cat.image!!.url!!)
            textView.text =cat.origin
            textView2.text = cat.description
            textView3.text = cat.temperament
            var url = intent.extras?.getString("Imagen")
            Glide.with(this)
                .load(cat.image!!.url!!)
                .placeholder(com.google.android.material.R.drawable.ic_clock_black_24dp)
                .centerCrop()
                .into(imageView)

            Log.d("que", "ondax3")


        }
        btnBack.setOnClickListener{
            startActivity(Intent(this@DescriptionCat, MainActivity::class.java))
        }
        }


    override fun onStart() {
        super.onStart()
        val btnFav : Button = findViewById(R.id.btnFavoritos)
        btnFav.setOnClickListener {




            Log.d("PRUEBA", "que onda");


            scope.launch {
                cat.favorito = true;
                MainRepository.guardarFavorito(this@DescriptionCat, cat)
                withContext(Dispatchers.Main) {
                    Log.d("prueba", "Se guardo el michi Fav")
                    Toast.makeText(
                        this@DescriptionCat,
                        "Gato Agregado a Favoritos",
                        Toast.LENGTH_SHORT
                        ).show()
                    }
                }



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