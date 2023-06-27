package com.example.anew.data.endpoints

import com.example.anew.data.connections.ApiConnection
import com.example.anew.data.entities.jukan.JikanAnimeEntity
import retrofit2.Response
import retrofit2.http.GET

interface JikanEndpoint {
    @GET("top/anime")
    suspend fun getAllAnimes(): Response<JikanAnimeEntity>


}