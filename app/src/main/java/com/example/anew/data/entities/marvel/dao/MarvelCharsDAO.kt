package com.example.anew.data.entities.marvel.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.anew.data.entities.marvel.database.MarvelCharsDB

@Dao
interface MarvelCharsDAO {

    @Query("select * from MarvelCharsDB")
    fun getAllCharacters() :List<MarvelCharsDB>

    @Query("select * from MarvelCharsDB where id = :pk")
    fun getOneCharacter(pk: Int) : MarvelCharsDB

    @Insert
    fun insertMarvelChar(ch : List<MarvelCharsDB>)
}