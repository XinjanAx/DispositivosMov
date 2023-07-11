package com.example.anew.logic.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class MarvelChars (
    val id: Int,
    val name: String,
    val comic: String,
    val img: String
) : Parcelable
