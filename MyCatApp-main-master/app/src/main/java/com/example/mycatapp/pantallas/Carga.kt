package com.example.mycatapp.pantallas


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.mycatapp.R
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth

class Carga : AppCompatActivity() {
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var fireBaseAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.progress_dialog)
        supportActionBar?.hide()
        //fireBaseAuth = FirebaseAuth.getInstance()

        //checkUser()
    }


    override fun onStart() {
        super.onStart()

        Handler(Looper.getMainLooper()).postDelayed({
            //checkUser()
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }

    fun checkUser() {
        val firebaseUser = fireBaseAuth.currentUser
        if (firebaseUser != null) {
            startActivity(Intent(this@Carga, MainActivity::class.java))
            finish()
        } else {
            startActivity(Intent(this@Carga, Login::class.java))
            finish()
        }
    }
}
