package com.example.anew.logic.jikanlogic

import com.example.anew.data.connections.*
import com.example.anew.endpoints.JikanEndpoint
import com.example.anew.logic.data.MarvelChars

class JikanAnimeLogic {

    suspend fun  getAllAnimes(): List<MarvelChars> {


        var itemList = arrayListOf<MarvelChars>()

        var response = ApiConnection.getService(
            ApiConnection.typeApi.Jikan, JikanEndpoint::class.java
        ).getAllAnimes()




        if (response.isSuccessful) {
            response.body()!!.data.forEach {
                val m = MarvelChars(
                    it.mal_id,
                    it.title,
                    it.titles[0].title,
                    it.images.jpg.image_url,
                )
                itemList.add(m)
            }

        }
        return itemList







    }


}