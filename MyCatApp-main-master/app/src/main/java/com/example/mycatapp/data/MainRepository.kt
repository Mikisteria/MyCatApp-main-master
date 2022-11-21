package com.example.mycatapp.data

import android.content.Context
import com.example.mycatapp.Cat

class MainRepository {
    suspend fun fetchData(context: Context) : ArrayList<Cat>{
        return ApiService.fetchData(context)
    }
}