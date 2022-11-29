package com.example.mycatapp.modelo

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Cat(
    var id:String? = null,
    var name: String? = null,
    var temperament: String? = null,
    var origin: String? = null,
    var description: String? = null,
    var image: CatImage? = null,
    var favorito: Boolean = false
) :Parcelable {

}
/*
    ) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readParcelable<CatImage>(CatImage::javaclass.ClassLoader))


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(temperament)
        parcel.writeString(origin)
        parcel.writeString(description)
        parcel.writeString(image.toString())
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Cat> {
        override fun createFromParcel(parcel: Parcel): Cat {
            return Cat(parcel)
        }

        override fun newArray(size: Int): Array<Cat?> {
            return arrayOfNulls(size)
        }
    }


}


 */