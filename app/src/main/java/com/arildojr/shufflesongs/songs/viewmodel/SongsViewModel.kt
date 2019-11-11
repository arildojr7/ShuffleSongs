package com.arildojr.shufflesongs.songs.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.arildojr.data.songs.SongsRepository
import com.arildojr.data.songs.model.Song
import com.arildojr.shufflesongs.core.base.BaseViewModel
import kotlinx.coroutines.launch

class SongsViewModel(private val songsRepository: SongsRepository) : BaseViewModel() {

    private val _songs = MutableLiveData<List<Song>>()
    val songs: LiveData<List<Song>> = Transformations.map(_songs) { it }

    companion object {
        private val artistIds = listOf("909253", "1171421960", "358714030", "1419227", "264111789")
    }

    fun getSongs() {
        launch {
            artistIds.forEach {

                _songs.postValue(songsRepository.getSongs(it).body())
            }
        }
    }
}