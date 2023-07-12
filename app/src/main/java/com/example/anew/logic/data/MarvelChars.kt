package com.example.anew.logic.data

import android.os.Parcelable
import com.example.anew.data.entities.marvel.database.MarvelCharsDB
import kotlinx.parcelize.Parcelize

@Parcelize
class MarvelChars (
    val id: Int,
    val name: String,
    val comic: String,
    val img: String
) : Parcelable

fun MarvelChars.getMarvelCharsDB() : MarvelCharsDB {
    return MarvelCharsDB(
        id,
        name,
        comic,
        img
    )
}
