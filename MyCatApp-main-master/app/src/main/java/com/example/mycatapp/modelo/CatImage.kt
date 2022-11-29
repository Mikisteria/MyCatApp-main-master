package com.example.mycatapp.modelo

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CatImage (
    var id: String? = null,
    var url: String? = null

    ) : Parcelable


