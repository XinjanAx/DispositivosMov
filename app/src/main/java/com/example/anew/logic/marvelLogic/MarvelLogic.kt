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

    suspend fun getAllMarvelChars(offset: Int, limit: Int): MutableList<MarvelChars> {

        var itemList = arrayListOf<MarvelChars>()

        val call = ApiConnection.getService(
            ApiConnection.typeApi.Marvel,
            MarvelEndpoints::class.java
        )

        if (call != null) {
            val response = call.getAllMarvelChars(offset, limit)

            if (response.isSuccessful) {
                response.body()!!.data.results.forEach() {
                    itemList.add(it.getMarvelChars())
                }
            } else {
                Log.d("UCE", response.toString())
            }
        }
        return itemList
    }

    suspend fun getAllMarvelCharsDB(): List<MarvelChars> {
        val items: ArrayList<MarvelChars> = arrayListOf()
        New.getDBInstance().marvelDao().getAllCharacters()
        .forEach {
            items.add(
                MarvelChars(
                    it.id,
                    it.nombre,
                    it.comic,
                    it.imagen
                )
            )
        }
        return items
    }

    suspend fun getInitChar(offset:Int, limit:Int): MutableList<MarvelChars> {
        var items = mutableListOf<MarvelChars>()
        try {
            items = MarvelLogic()
                .getAllMarvelCharsDB()
                .toMutableList()

            if (items.isEmpty()) {
                items = (MarvelLogic().getAllMarvelChars(
                    offset,
                    limit
                ))
                //MarvelLogic().insertMarvelCharsToDB(items)
            }
        } catch (ex: Exception) {
            throw RuntimeException(ex.message)
        } finally {
            return items
        }
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