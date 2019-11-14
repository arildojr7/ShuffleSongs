package com.arildojr.shufflesongs.songs.viewmodel

import androidx.lifecycle.MutableLiveData
import com.arildojr.data.songs.SongsRepository
import com.arildojr.data.songs.enum.WrapperTypeEnum
import com.arildojr.data.songs.model.Song
import com.arildojr.shufflesongs.core.base.BaseViewModel
import com.arildojr.shufflesongs.core.util.*

class SongsViewModel(
    private val songsRepository: SongsRepository,
    private val commandProvider: CommandProvider
) : BaseViewModel() {

    companion object {
        private val artistIds = listOf("909253", "1171421960", "358714030", "1419227", "264111789")
        private const val LIMIT_SONGS = 5
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
            val response = songsRepository.getSongs(artistIds.joinToString(","), LIMIT_SONGS)

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

    fun shuffleSongs() {
        if (command.value is Command.LoadSongs) {
            val songs = (command.value as Command.LoadSongs).songs.shuffled()
            val group = songs.groupBy { it.artistId }
            val songsShuffled = combine(group.values.toTypedArray())

            tryCast<List<Song>>(songsShuffled) {
                command.postValue(Command.LoadSongs(this))
            }
        }
    }

    sealed class Command : GenericCommand() {
        object ErrorOnLoadSongs : Command()
        class LoadSongs(val songs: List<Song>) : Command()
    }

    data class ViewState(
        val isLoadingSongs: Boolean = false
    )
}