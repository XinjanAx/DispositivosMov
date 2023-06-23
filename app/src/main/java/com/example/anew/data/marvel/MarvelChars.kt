package com.example.anew.data.marvel

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class MarvelChars (
    var id:Int,
    var name: String,
    val comic: String,
    val img: String
) : Parcelable
