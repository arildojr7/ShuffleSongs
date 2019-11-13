package com.arildojr.data.songs

import com.arildojr.data.songs.datasource.remote.SongsRemoteDataSource
import com.arildojr.data.songs.model.ResponseWrapper
import com.arildojr.data.songs.model.Song
import retrofit2.Response

class SongsRepositoryImpl(private val songsRemoteDataSource: SongsRemoteDataSource) : SongsRepository {
    override suspend fun getSongs(artistId: String, limit: Int): Response<ResponseWrapper<Song>> {
        return songsRemoteDataSource.getSongs(artistId, limit)
    }
}