package com.example.mycatapp.data

import android.content.Context
import android.util.Log
import com.example.mycatapp.modelo.Cat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.delay
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiService {
    companion object{
        val BASE_URL = "https://api.thecatapi.com/v1/"
        private val db = FirebaseFirestore.getInstance()
        private lateinit var firebaseAuth: FirebaseAuth

        suspend fun fetchData(context: Context) : ArrayList<Cat>{
            Log.d("API-DEMO", "Call to API Started")

            val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val api = retrofit.create(CatApi::class.java)

            val result = api.getKitties().execute()

            Log.d("API-DEMO", "Call to API Finished")

            return if (result.isSuccessful){
                delay(5000)
                result.body()!!
            }
            else {
                Log.d("API-DEMO", "Error recuperando razas" + result.errorBody().toString())
                ArrayList<Cat>()
            }
        }

    suspend fun guardarFavorito(context: Context, cat: Cat){
        firebaseAuth = FirebaseAuth.getInstance()
        val firebaseUser = firebaseAuth.currentUser
        val email : String = firebaseUser?.email!!

        db.collection("usuarios")
            .document(email)
            .collection("favoritos")
            .document(cat.id.toString())
            .set(
                hashMapOf(
                    "id" to cat.id,
                    "name" to cat.name,
                    "image" to cat.image,
                    "origin" to cat.origin,
                    "temperament" to cat.temperament,
                    "description" to cat.description,
                    "favorito" to true

                )
            )
    }
    suspend fun eliminarFavorito(context: Context, cat: Cat){
        firebaseAuth = FirebaseAuth.getInstance()
        val firebaseUser = firebaseAuth.currentUser
        val email : String = firebaseUser?.email!!
        Log.d("PRUEBA",email);

        db.collection("usuarios")
            .document(email)
            .collection("favoritos")
            .document(cat.id.toString())
            .delete()
            .addOnSuccessListener {
                Log.d("prueba","ApiService: Gato eliminado de favoritos")
            }
            .addOnSuccessListener {
                Log.d("prueba","ApiService: Error: no se pudo eliminar de fav")
            }

    }



    suspend fun fetchCatFavoritos(context: Context): ArrayList<Cat>?{
        firebaseAuth = FirebaseAuth.getInstance()
        val firebaseUser = firebaseAuth.currentUser
        val email : String = firebaseUser?.email!!
        var kitties = ArrayList<Cat>()

        db.collection("usuarios").document(email).collection("favoritos")
            .get()
            .addOnSuccessListener { documents ->
                if(documents != null) {
                    for (document in documents) {
                        Log.d("GetFirebase", "${document.id} => ${document.data}")
                        var cat = document.toObject(Cat::class.java)
                        Log.d("GetFirebase", "Convertido a objeto: => ${cat}")
                        kitties.add(cat)
                        }
                    }else{
                        Log.d("GetFirebase", "No such document")
                    }
                }
            .addOnFailureListener{ exception ->
                Log.d("Get firebase","se re rompio" )
            }
        return kitties
        }
    }

}