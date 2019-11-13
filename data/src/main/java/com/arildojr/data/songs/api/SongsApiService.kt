package com.arildojr.data.songs.api

import com.arildojr.data.songs.model.ResponseWrapper
import com.arildojr.data.songs.model.Song
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SongsApiService {

    @GET("/lookup")
    suspend fun getSongs(@Query("id") artistId: String, @Query("limit") limit: Int): Response<ResponseWrapper<Song>>
}