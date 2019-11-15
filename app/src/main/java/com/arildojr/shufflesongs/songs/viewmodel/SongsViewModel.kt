package com.arildojr.shufflesongs.songs.viewmodel

import androidx.lifecycle.MutableLiveData
import com.arildojr.data.songs.SongsRepository
import com.arildojr.data.songs.enum.WrapperTypeEnum
import com.arildojr.data.songs.exception.FailureRequestException
import com.arildojr.data.songs.exception.FailureRequestWithLocalDataException
import com.arildojr.data.songs.model.Song
import com.arildojr.shufflesongs.core.base.BaseViewModel
import com.arildojr.shufflesongs.core.util.*
import kotlinx.coroutines.flow.collect

class SongsViewModel(
    private val songsRepository: SongsRepository,
    private val commandProvider: CommandProvider
) : BaseViewModel() {

    companion object {
        private const val LIMIT_SONGS = 5
    }

    val command: SingleLiveEvent<GenericCommand> = commandProvider.getCommand()
    val viewState: MutableLiveData<ViewState> = MutableLiveData()
    private val actualSongs by lazy { (command.value as? Command.LoadSongs)?.songs?.shuffled() ?: emptyList() }
    private fun currentViewState(): ViewState = viewState.value ?: ViewState()

    init {
        viewState.value = ViewState()
    }

    suspend fun getSongs(artistIds: List<String>) {
        viewState.value = currentViewState().copy(isLoadingSongs = true)

        try {
            songsRepository.getSongs(artistIds.joinToString(","), LIMIT_SONGS).collect { response ->
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
            }

        } catch (e: Exception) {
            when(e) {
                is FailureRequestException -> {
                    viewState.value = currentViewState().copy(isLoadingSongs = false)
                    command.postValue(Command.ErrorOnLoadSongs)
                }
                is FailureRequestWithLocalDataException -> {
                    // show a toast to notify data is only local
                }
            }
        }
    }

    fun shuffleSongs(songList: List<Song> = actualSongs) {
        val songs = songList.shuffled()
        val group = songs.groupBy { it.artistId }
        val songsShuffled = combine(group.values.toTypedArray())

        tryCast<List<Song>>(songsShuffled) {
            command.postValue(Command.LoadSongs(this))
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