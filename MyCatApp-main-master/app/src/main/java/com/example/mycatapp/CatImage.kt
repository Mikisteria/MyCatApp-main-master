package com.example.mycatapp

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CatImage (
    val id: String,
    val url: String

    ) : Parcelable


