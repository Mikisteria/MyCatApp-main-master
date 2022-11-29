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
            val origin : TextView = findViewById(R.id.lblOrigin)
            val description : TextView = findViewById(R.id.lblDescription)
            val temperament : TextView = findViewById(R.id.lblTemperament)
            val name : TextView = findViewById(R.id.lblName)
            Log.d("que", "ondax2")
            Log.d("que", cat.image!!.url!!)
            origin.text =cat.origin
            description.text = cat.description
            temperament.text = cat.temperament
            name.text = cat.name
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






