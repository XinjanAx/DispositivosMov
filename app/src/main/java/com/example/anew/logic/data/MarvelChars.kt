package com.example.anew.logic.data

import android.os.Parcelable
import com.example.anew.data.entities.marvel.database.MarvelCharsDB
import kotlinx.parcelize.Parcelize

@Parcelize
class MarvelChars (
    val id: Int,
    val nombre: String,
    val comic: String,
    val imagen: String
) : Parcelable

fun MarvelChars.getMarvelCharsDB() : MarvelCharsDB {
    return MarvelCharsDB(
        id,
        nombre,
        comic,
        imagen
    )
}
