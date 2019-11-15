package com.arildojr.data.songs

import com.arildojr.data.songs.model.ResponseWrapper
import com.arildojr.data.songs.model.Song
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface SongsRepository {
    suspend fun getSongs(artistId: String, limit: Int): Flow<Response<ResponseWrapper<Song>>>

    suspend fun saveSongs(songs: List<Song>)
}