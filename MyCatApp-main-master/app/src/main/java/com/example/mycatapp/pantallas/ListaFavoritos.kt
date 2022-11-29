package com.example.mycatapp.pantallas

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mycatapp.R
import com.example.mycatapp.data.ApiService
import com.example.mycatapp.data.ApiService.Companion.fetchCatFavoritos
import com.example.mycatapp.data.MainRepository
import com.example.mycatapp.modelo.Cat
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext


class ListaFavoritos : AppCompatActivity(){

    private val coroutineContext: CoroutineContext = newSingleThreadContext("Main")
    private val scope = CoroutineScope(coroutineContext)

    private lateinit var adapter : CatAdapter
    private lateinit var rvKitties : RecyclerView
    private var kitties = ArrayList<Cat>()
    private lateinit var titulo: String
    private lateinit var cat : Cat

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favoritos)
        supportActionBar?.hide()



        val btnHome = findViewById<Button>(R.id.BtnHome)
        btnHome.setOnClickListener{
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        rvKitties = findViewById<RecyclerView>(R.id.rvKitties)
        rvKitties.layoutManager = LinearLayoutManager(this)
        adapter = CatAdapter(kitties, this)
        rvKitties.adapter = adapter

        adapter.onItemClick = { kitties: Cat ->
            val intent = Intent(this,DescriptionCat::class.java)
            intent.putExtra("Titulo", titulo)
            intent.putExtra("Nombre", kitties.name)
            intent.putExtra("Descripcion", kitties.description)
            intent.putExtra("Imagen",kitties.image)
            intent.putExtra("Id", kitties.id)
            intent.putExtra("Origen",kitties.origin)
            intent.putExtra("Temperamento",kitties.temperament)
            intent.putExtra("Favorito",kitties.favorito)

            startActivity(intent)
            finish()
        }




    }

    override fun onStart() {
        super.onStart()

        var textCargando = findViewById<TextView>(R.id.tv_cargando)
        val btnBorrar : Button = findViewById(R.id.btnBorrar)

        btnBorrar.setOnClickListener{
            scope.launch {
                cat.favorito = false;
                MainRepository.eliminarFavorito(this@ListaFavoritos, cat)
                withContext(Dispatchers.Main) {
                    Log.d("prueba", "Se Elimino el Gato de Fav")
                    Toast.makeText(
                        this@ListaFavoritos,
                        "Gato Eliminado de Favoritos",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                cat.favorito = false;
            }
        }



        scope.launch {

            kitties = MainRepository.fetchCatFavorito(this@ListaFavoritos)!!

            withContext(Dispatchers.Main){
                if(kitties.size>0){
                    adapter.Update(kitties)
                    textCargando.visibility = View.INVISIBLE
                }
                else{
                    textCargando.text = "No hay gatos guardados en favoritos"
                }
            }
        }



    }

}


/*


class ListaFavoritos(var items: MutableList<Cat>, context: Context) : RecyclerView.Adapter<CatViewHolder>() {
    private val context = context

    var onItemClick : ((Cat) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_favoritos, parent, false)
        return CatViewHolder(view)

    }

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
        holder.name.text = items[position].name


        Log.d(ContentValues.TAG, items[position].image!!.url)

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



 */

