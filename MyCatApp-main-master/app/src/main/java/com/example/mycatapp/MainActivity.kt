package com.example.mycatapp

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mycatapp.data.CustomProgressDialog
import com.example.mycatapp.data.MainRepository
import com.example.mycatapp.databinding.ActivityMainBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
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
    val firebaseAuth = FirebaseAuth.getInstance()
    private lateinit var searchView: SearchView
    private lateinit var imageView: ImageView
    lateinit var mGoogleSignInClient : GoogleSignInClient


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

        btnLogout = findViewById(R.id.btnLogout)
        btnLogout.setOnClickListener {
            mGoogleSignInClient.signOut().addOnCompleteListener {
                firebaseAuth.signOut()
                checkUser()
                startActivity(Intent(this@MainActivity, Login::class.java))

            }
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
    }

    private fun buscarRazas() {
        searchView = findViewById(R.id.searchView)
        //searchView.setOnQueryTextListener()
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
            kitties = MainRepository().fetchData(context)
            Log.d("API-DEMO", kitties.size.toString())
            withContext(Dispatchers.Main) {
                adapter.Update(kitties)
                progressDialog.stop()
            }
        }
    }


}