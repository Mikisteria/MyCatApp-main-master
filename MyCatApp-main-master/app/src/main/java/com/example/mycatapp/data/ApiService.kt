package com.example.mycatapp.data

import android.content.Context
import android.util.Log
import com.example.mycatapp.Cat
import kotlinx.coroutines.delay
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiService {
    companion object{
        val BASE_URL = "https://api.thecatapi.com/v1/"

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
    }
}