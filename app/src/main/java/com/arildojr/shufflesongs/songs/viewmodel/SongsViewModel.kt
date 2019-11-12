package com.arildojr.shufflesongs.songs.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.arildojr.data.songs.SongsRepository
import com.arildojr.data.songs.enum.WrapperTypeEnum
import com.arildojr.data.songs.model.Song
import com.arildojr.shufflesongs.core.base.BaseViewModel
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import java.util.*
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.util.Log
import kotlin.math.abs


class SongsViewModel(private val songsRepository: SongsRepository) : BaseViewModel() {

    companion object {
        private val artistIds = listOf("909253", "1171421960", "358714030", "1419227", "264111789")
    }

    private val _songs = MutableLiveData<List<Song>>()
    val songs: LiveData<List<Song>> = Transformations.map(_songs) { it }

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = Transformations.map(_isLoading) { it }

    suspend fun getSongs() {
        _isLoading.postValue(true)

        val response = songsRepository.getSongs(artistIds.joinToString(","))
        _songs.postValue(response?.filter { it.wrapperType == WrapperTypeEnum.TRACK.getValue() })

        _isLoading.postValue(false)
    }

    fun shuffleSongs(songs: List<Song> = _songs.value.orEmpty()) {
        val shuffledSongs = songs.shuffled()
        val auxList = mutableListOf<Song>()

        shuffledSongs.forEachIndexed { index, song ->
            when {
                index == 0  -> auxList.add(song)
                song.artistId != shuffledSongs[index-1].artistId -> auxList.add(song)
                else -> Log.e(">>>>> Count not added: ", song.artistName)
            }
        }

        if (auxList.isEmpty())  _songs.postValue(auxList)
    }

}