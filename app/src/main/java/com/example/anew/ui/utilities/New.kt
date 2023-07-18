package com.example.anew.ui.utilities

import android.app.Application
import androidx.room.Room
import com.example.anew.data.connections.MarvelConnectionDB

class New : Application(){

    override fun onCreate(){
        super.onCreate()
        db = Room.databaseBuilder(applicationContext,
            MarvelConnectionDB::class.java,
            "marvelDB").build()
    }

    companion object{
        private var db : MarvelConnectionDB? = null

        fun getDBInstance() : MarvelConnectionDB {
            //!! -> porque nunca va a ser nula
            return db!!
        }
    }
}