package com.example.anew.data.connections

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.anew.data.entities.marvel.dao.MarvelCharsDAO
import com.example.anew.data.entities.marvel.database.MarvelCharsDB

@Database(
    entities = [MarvelCharsDB::class],
    version = 1
)
abstract class MarvelConnectionDB : RoomDatabase() {

    abstract fun marvelDao() : MarvelCharsDAO

}