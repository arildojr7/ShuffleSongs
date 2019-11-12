package com.arildojr.shufflesongs.songs.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.arildojr.data.songs.SongsRepository
import com.arildojr.data.songs.model.Song
import com.arildojr.shufflesongs.core.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SongsViewModel(private val songsRepository: SongsRepository) : BaseViewModel() {

    companion object {
        private val artistIds = listOf("909253", "1171421960", "358714030", "1419227", "264111789")
    }

    private val _songs = MutableLiveData<List<Song>>()
    val songs: LiveData<List<Song>> = Transformations.map(_songs) { it }

    suspend fun getSongs() {
        artistIds.forEach { artistId ->
            _songs.value.let { loadedSongs ->
                val songs = getSong(artistId)?.toMutableList()
                if (loadedSongs != null) songs?.addAll(loadedSongs)
                _songs.value = songs?.sortedBy { it.trackName }
            }
        }
    }

    private suspend fun getSong(artistId: String): List<Song>? {
        return withContext(Dispatchers.Default) {
            songsRepository.getSongs(artistId)
        }
    }
}