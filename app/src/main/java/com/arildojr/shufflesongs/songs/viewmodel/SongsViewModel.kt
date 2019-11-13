package com.arildojr.shufflesongs.songs.viewmodel

import androidx.lifecycle.MutableLiveData
import com.arildojr.data.songs.SongsRepository
import com.arildojr.data.songs.enum.WrapperTypeEnum
import com.arildojr.data.songs.model.Song
import com.arildojr.shufflesongs.core.base.BaseViewModel
import com.arildojr.shufflesongs.core.util.CommandProvider
import com.arildojr.shufflesongs.core.util.GenericCommand
import com.arildojr.shufflesongs.core.util.SingleLiveEvent

class SongsViewModel(
    private val songsRepository: SongsRepository,
    private val commandProvider: CommandProvider
) : BaseViewModel() {

    companion object {
        private val artistIds = listOf("909253", "1171421960", "358714030", "1419227", "264111789")
    }

    val command: SingleLiveEvent<GenericCommand> = commandProvider.getCommand()
    val viewState: MutableLiveData<ViewState> = MutableLiveData()
    private fun currentViewState(): ViewState = viewState.value ?: ViewState()

    init {
        viewState.value = ViewState()
    }

    suspend fun getSongs() {
        viewState.value = currentViewState().copy(isLoadingSongs = true)

        try {
            val response = songsRepository.getSongs(artistIds.joinToString(","))

            viewState.value = currentViewState().copy(isLoadingSongs = false)

            if (response.isSuccessful) {
                response.body()?.results?.let { songList ->
                    command.postValue(
                        Command.LoadSongs(songList.filter { it.wrapperType == WrapperTypeEnum.TRACK.getValue() })
                    )
                }
            } else {
                command.postValue(Command.ErrorOnLoadSongs)
            }

        } catch (e: Exception) {
            viewState.value = currentViewState().copy(isLoadingSongs = false)
            command.postValue(Command.ErrorOnLoadSongs)
        }

    }

    fun shuffleSongs(songs: List<Song> = emptyList()) {
        val shuffledSongs = songs.shuffled()
        val auxList = mutableListOf<Song>()

        shuffledSongs.forEachIndexed { index, song ->
            when {
                index == 0 -> auxList.add(song)
                song.artistId != shuffledSongs[index - 1].artistId -> auxList.add(song)
                else -> {

                }
            }
        }

    }

    data class ViewState(
        val isLoadingSongs: Boolean = false
    )

    sealed class Command : GenericCommand() {
        object ErrorOnLoadSongs : Command()
        class LoadSongs(val songs: List<Song>) : Command()
    }

}