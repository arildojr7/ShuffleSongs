package com.arildojr.data.songs.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.arildojr.data.songs.model.Song
import kotlinx.coroutines.flow.Flow

@Dao
interface SongDao {
    @Query("SELECT * FROM songs")
    fun getAll(): Flow<List<Song>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(songs: List<Song>)
}