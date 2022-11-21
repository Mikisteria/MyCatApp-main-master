package com.example.mycatapp.data

import com.example.mycatapp.Cat
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CatApi {
    @GET("/images/search")
    fun getCat(@Query("breed_ids") breeds: String) : Call<Cat>

    @GET("breeds")
    fun getKitties() : Call<ArrayList<Cat>>
}