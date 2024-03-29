package com.example.mycatapp.pantallas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mycatapp.databinding.ActivityProfileBinding
import com.google.firebase.auth.FirebaseAuth

class ProfileActivity : AppCompatActivity() {

    //view binding
    private lateinit var binding: ActivityProfileBinding

    //firebase auth
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //init firebase auth
        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()

        //handle click, logout user
        binding.logoutBtn.setOnClickListener{
            firebaseAuth.signOut()
            checkUser()
        }
    }

    private fun checkUser() {
        //get current user
        val firebaseUser = firebaseAuth.currentUser
        if(firebaseUser == null){
            //user not logged in
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        else{
            //user logged in
            //get user info
            val email = firebaseUser.email
            //set email
            binding.emailTv.text = email
        }
    }
}