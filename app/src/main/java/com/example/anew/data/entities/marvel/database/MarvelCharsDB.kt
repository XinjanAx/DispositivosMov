package com.example.anew.data.entities.marvel.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.anew.logic.data.MarvelChars
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
class MarvelCharsDB (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val nombre: String,
    val comic: String,
    val imagen: String
    ): Parcelable

fun MarvelCharsDB.getMarvelChars() : MarvelChars {
    return MarvelChars(
        id,
        nombre,
        comic,
        imagen
    )
}
