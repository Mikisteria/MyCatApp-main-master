package com.example.mycatapp.data

import android.content.Context
import com.example.mycatapp.modelo.Cat

class MainRepository {
    companion object {
        suspend fun fetchData(context: Context): ArrayList<Cat> {
            return ApiService.fetchData(context)
        }

        suspend fun guardarFavorito(context: Context, cat: Cat) {
            ApiService.guardarFavorito(context, cat)
        }

        suspend fun eliminarFavorito(context: Context, cat: Cat) {
            ApiService.eliminarFavorito(context, cat)
        }

        suspend fun fetchCatFavorito(context: Context): ArrayList<Cat>? {
            return ApiService.fetchCatFavoritos(context)

        }
    }
}