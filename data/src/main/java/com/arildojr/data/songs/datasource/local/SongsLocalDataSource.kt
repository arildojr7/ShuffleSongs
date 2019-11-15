package com.arildojr.data.songs.datasource.local

import com.arildojr.data.songs.SongsDataSource
import com.arildojr.data.songs.datasource.local.database.SongsDatabase
import com.arildojr.data.songs.model.Song
import kotlinx.coroutines.flow.Flow

class SongsLocalDataSource(private val appDatabase: SongsDatabase) : SongsDataSource.Local {
    override suspend fun getSongs(): Flow<List<Song>> {
        return appDatabase.songDao().getAll()
    }

    override suspend fun saveSongs(songs: List<Song>) {
        appDatabase.songDao().insertAll(songs)
    }

}