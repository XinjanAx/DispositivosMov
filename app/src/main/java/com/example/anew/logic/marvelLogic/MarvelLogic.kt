package com.example.anew.logic.marvelLogic

import android.util.Log
import com.example.anew.data.connections.ApiConnection
import com.example.anew.data.entities.marvel.getMarvelChars
import com.example.anew.data.endpoints.MarvelEndpoints
import com.example.anew.data.entities.marvel.database.MarvelCharsDB
import com.example.anew.data.entities.marvel.database.getMarvelChars
import com.example.anew.logic.data.MarvelChars
import com.example.anew.logic.data.getMarvelCharsDB
import com.example.anew.ui.utilities.New

class MarvelLogic {

    suspend fun getMarvelChars(name: String, limit: Int): ArrayList<MarvelChars> {

        var itemList = arrayListOf<MarvelChars>()

        var response = ApiConnection.getService(
            ApiConnection.typeApi.Marvel,
            MarvelEndpoints::class.java
        ).getCharacterStartWhit(name, limit)

        if (response.isSuccessful) {
            response.body()!!.data.results.forEach {
                val m = it.getMarvelChars()
                itemList.add(m)
            }
        } else {
            Log.d("UCE", response.toString())
        }

        return itemList
    }

    suspend fun getAllMarvelChars(offset: Int, limit: Int): ArrayList<MarvelChars> {

        val itemList = arrayListOf<MarvelChars>()

        val response = ApiConnection.getService(
            ApiConnection.typeApi.Marvel,
            MarvelEndpoints::class.java
        ).getAllMarvelChars(offset, limit)

        if(response != null){
            response.body()!!.data.results.forEach {
                val m = it.getMarvelChars()
                itemList.add(m)
            }
        }
        return itemList
    }

    suspend fun getAllMarvelCharsDB(): List<MarvelChars> {
        var itemList : ArrayList<MarvelChars> = arrayListOf()
        val items_aux =
            New.getDBInstance().marvelDao().getAllCharacters()
        items_aux.forEach {
            itemList.add(it.getMarvelChars())
        }
        return itemList
    }

    suspend fun insertMarvelCharsToDB(items : List<MarvelChars>) {
        var itemsDB = arrayListOf<MarvelCharsDB>()
        items.forEach {
            itemsDB.add(it.getMarvelCharsDB())
        }

        New
            .getDBInstance()
            .marvelDao()
            .insertMarvelChar(itemsDB)
    }


}