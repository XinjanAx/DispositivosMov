package com.example.anew.logic.marvelLogic

import com.example.anew.data.connections.ApiConnection
import com.example.anew.data.endpoints.JikanEndpoint
import com.example.anew.data.endpoints.MarvelEndpoints
import com.example.anew.data.marvel.MarvelChars

class MarvelLogic {

    suspend fun  getAllMarvel(name:String, limit:Int): List<MarvelChars> {

        var itemList = arrayListOf<MarvelChars>()

        var call = ApiConnection.getService(
            ApiConnection.typeApi.Marvel, MarvelEndpoints::class.java
        )

        if(call != null){
            val response=call.getCharacterStartWhit(name,limit)

            if (response.isSuccessful) {
                response.body()!!.data.results.forEach {
                    var comic: String = "No available"
                    if (it.comics.items.size>0) comic=it.comics.items[0].name
                    val m = MarvelChars(
                        it.id,
                        it.name,
                        comic,
                        it.thumbnail.path +"."+it.thumbnail.extension,

                        )
                    itemList.add(m)
                }

            }

        }

        return itemList



    }



}