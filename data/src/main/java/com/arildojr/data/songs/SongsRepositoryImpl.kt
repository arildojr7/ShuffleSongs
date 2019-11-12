package com.arildojr.data.songs

import com.arildojr.data.songs.datasource.remote.SongsRemoteDataSource
import com.arildojr.data.songs.model.Song
import retrofit2.Response

class SongsRepositoryImpl(private val songsRemoteDataSource: SongsRemoteDataSource) : SongsRepository {
    override suspend fun getSongs(artistId: String): List<Song>? {
        return songsRemoteDataSource.getSongs(artistId).body()?.results
    }
}