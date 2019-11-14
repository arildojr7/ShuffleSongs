package com.arildojr.data.songs.datasource.remote

import com.arildojr.data.songs.SongsDataSource
import com.arildojr.data.songs.api.SongsApiService
import com.arildojr.data.songs.model.ResponseWrapper
import com.arildojr.data.songs.model.Song
import retrofit2.Response

class SongsRemoteDataSource(private val apiService: SongsApiService) : SongsDataSource.Remote {
    override suspend fun getSongs(artistId: String, limit: Int): Response<ResponseWrapper<Song>> {
        return apiService.getSongs(artistId, limit)
    }
}