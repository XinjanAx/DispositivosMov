package com.example.anew.data.connections

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConnection {

    enum class typeApi {
        Jikan, Marvel
    }

    val API_JIKAN = "https://api.jikan.moe/v4/"
    val API_MARVEL = "https://gateway.marvel.com/v1/public/"

    private fun getConnection(base:String): Retrofit {

        var retrofit = Retrofit.Builder()
            .baseUrl(base)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

        return retrofit
    }

    suspend fun <T,E :Enum<E>> getService(api: typeApi, service:Class<T>):T{
        var BACE =""
        when(api.name){
            typeApi.Jikan.name -> {
                BACE = API_JIKAN
            }
            typeApi.Marvel.name -> {
                BACE= API_MARVEL
            }
        }
        return getConnection(BACE).create(service)
    }
}