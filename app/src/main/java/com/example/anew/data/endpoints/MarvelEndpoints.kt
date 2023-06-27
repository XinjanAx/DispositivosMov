package com.example.anew.data.endpoints

import com.example.anew.data.entities.marvel.MarvelApiChars
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface MarvelEndpoints {
    @GET("characters")
    suspend fun getCharacterStartWhit(
        @Query("nameStartsWhit") name:String,
        @Query("limit") limit : Int,
        @Query("ts")ts:String = "uce",
        @Query("apikey")apikey:String ="48ed26ff242038147ce24450236a7ec2",
        @Query("hash")hash:String ="f00af94ad24dd1d56b2ea26ae903030e"
    ):Response<MarvelApiChars>
}