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
    val name: String,
    val comic: String,
    val img: String
    ): Parcelable

fun MarvelCharsDB.getMarvelChars() : MarvelChars {
    return MarvelChars(
        id,
        name,
        comic,
        img
    )
}
