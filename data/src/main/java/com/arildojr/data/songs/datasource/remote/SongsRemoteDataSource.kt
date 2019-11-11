package com.arildojr.data.songs.datasource.remote

import com.arildojr.data.songs.SongsDataSource
import com.arildojr.data.songs.model.Song
import retrofit2.Response

class SongsRemoteDataSource : SongsDataSource {
    override suspend fun getSongs(artistId: String): Response<List<Song>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}