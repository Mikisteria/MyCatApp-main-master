package com.example.mycatapp.pantallas

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mycatapp.R
import com.example.mycatapp.data.CustomProgressDialog
import com.example.mycatapp.data.MainRepository
import com.example.mycatapp.databinding.ActivityMainBinding
import com.example.mycatapp.modelo.Cat
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity() {
    private val corountineContext: CoroutineContext = newSingleThreadContext("main")
    private val scope = CoroutineScope(corountineContext)
    private var kitties = ArrayList<Cat>()
    private lateinit var adapter : CatAdapter
    private lateinit var rvKitties : RecyclerView
    private val progressDialog by lazy { CustomProgressDialog(this) }
    private lateinit var binding: ActivityMainBinding
    private lateinit var btnLogout : Button
    private lateinit var btnFav : Button
    val firebaseAuth = FirebaseAuth.getInstance()
    private lateinit var searchView: SearchView
    private lateinit var imageView: ImageView
    lateinit var mGoogleSignInClient : GoogleSignInClient
    private lateinit var adapter2 : DescriptionCatAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "llego?")

        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        rvKitties = findViewById<RecyclerView>(R.id.rvKitties)
        rvKitties.layoutManager = LinearLayoutManager(this)
        adapter = CatAdapter(kitties, this)
        rvKitties.adapter = adapter

        btnLogout = findViewById(R.id.btnLogOut)
        btnLogout.setOnClickListener {
            mGoogleSignInClient.signOut().addOnCompleteListener {
                firebaseAuth.signOut()
                checkUser()
                startActivity(Intent(this@MainActivity, Login::class.java))

            }
        }

        btnFav = findViewById(R.id.btnFavPan)

        btnFav.setOnClickListener{
            val intent = Intent(this, ListaFavoritos::class.java)
            startActivity(intent)
        }

        // Configure the Google Sign OUT
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)


        adapter.onItemClick ={
            val intent = Intent(this, DescriptionCat::class.java)
            intent.putExtra("cat", it)
            startActivity(intent)
        }

    /*

        adapter.onItemClick = { kitties: Cat ->
            val intent = Intent(this, DescriptionCat::class.java)
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

*/

    }





    private fun init(){
        rvKitties = findViewById<RecyclerView>(R.id.rvKitties)
        rvKitties.setHasFixedSize(true)
        rvKitties.layoutManager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)


        //addDataToList()

        adapter = CatAdapter(kitties,this)
        rvKitties.adapter = adapter
    }




    override fun onStart() {
        super.onStart()
        progressDialog.start("Recuperando Datos...")

        start(this)
        var searchInput = findViewById<TextInputEditText>(R.id.inputFiltro)
        // filtro
        searchInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(search: CharSequence?, start: Int, before: Int, count: Int) {
                val gatos = buscarGatosPorNombre(kitties,search);
                updateKittiesQuery(gatos)
            }
            private fun buscarGatosPorNombre( gatitos: ArrayList<Cat>, search: CharSequence?): ArrayList<Cat> {
                val gatitosEncontrados = ArrayList<Cat>();
                for(gatito in gatitos){
                    if  (gatito.name?.uppercase()?.contains(search.toString().uppercase()) == true) {
                        gatitosEncontrados.add(gatito)
                    }
                }
                return gatitosEncontrados
            }
            private fun updateKittiesQuery(gatitos: ArrayList<Cat>) {
                scope.launch {
                    withContext(Dispatchers.Main) {
                        adapter.Update(gatitos)
                    }
                }
            }
            override fun afterTextChanged(s: Editable?) {
            }
        })

    }




    private fun checkUser() {

        //check if user is logged in or not
        val firebaseUser = firebaseAuth.currentUser
        if(firebaseUser != null){
            //user is already logged in
            //Start profile activity
            startActivity(Intent(this@MainActivity, Login::class.java))
            finish()
        }
    }




    fun start(context: Context){
        scope.launch{
            Log.d("API-DEMO", "recuperando")
            kitties = MainRepository.fetchData(context)
            Log.d("API-DEMO", kitties.size.toString())
            withContext(Dispatchers.Main) {
                adapter.Update(kitties)
                progressDialog.stop()
            }
        }
    }


}