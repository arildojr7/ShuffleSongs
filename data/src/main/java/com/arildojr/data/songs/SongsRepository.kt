package com.arildojr.data.songs

import com.arildojr.data.songs.model.Song
import retrofit2.Response
import retrofit2.http.Query

interface SongsRepository {
    suspend fun getSongs(@Query("id") artistId: String): List<Song>?
}