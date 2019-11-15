package com.arildojr.data.songs

import com.arildojr.data.songs.model.ResponseWrapper
import com.arildojr.data.songs.model.Song
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface SongsDataSource {

    interface Local {
        suspend fun getSongs() : Flow<List<Song>>
        suspend fun saveSongs(songs: List<Song>)
    }

    interface Remote {
        suspend fun getSongs(artistId: String, limit: Int): Response<ResponseWrapper<Song>>
    }
}