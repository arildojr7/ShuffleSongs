package com.arildojr.shufflesongs.songs.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.arildojr.data.songs.SongsRepository
import com.arildojr.data.songs.model.ResponseWrapper
import com.arildojr.data.songs.model.Song
import com.arildojr.shufflesongs.BaseTest
import com.arildojr.shufflesongs.core.util.CommandProvider
import com.arildojr.shufflesongs.core.util.GenericCommand
import com.arildojr.shufflesongs.core.util.SingleLiveEvent
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class SongsViewModelTest : BaseTest() {

    @JvmField
    @Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var songsRepositoryMock: SongsRepository
    private lateinit var commandProviderMock: CommandProvider
    private lateinit var commandMock: SingleLiveEvent<GenericCommand>

    private lateinit var viewStateObserver: Observer<SongsViewModel.ViewState>
    private lateinit var songsViewModel: SongsViewModel

    private val SONGS_DATA_FILE = "songs.json"

    @Before
    fun setUp() {
        songsRepositoryMock = mock()
        commandProviderMock = mock()
        viewStateObserver = mock()
        commandMock = mock()

        whenever(commandProviderMock.getCommand()).thenReturn(commandMock)

        songsViewModel = SongsViewModel(
            songsRepositoryMock,
            commandProviderMock
        )
    }

    @Test
    fun getSongs() = runBlocking {
        // ARRANGE
        val expectedSongs = createTestData<List<Song>>(loadJsonFromAsset(SONGS_DATA_FILE))
        val expectedSongsCount = 5
        val expectedViewState = SongsViewModel.ViewState()

        songsViewModel.viewState.observeForever(viewStateObserver)

        val expectedLoadSongsCommand = SongsViewModel.Command.LoadSongs(expectedSongs)
        val expectedSongsResponse = Response.success(ResponseWrapper(expectedSongsCount, expectedSongs))

        whenever(
            songsRepositoryMock.getSongs(
                expectedSongs.joinToString(",") { it.artistId.toString() },
                expectedSongsCount
            )
        ).thenReturn(expectedSongsResponse)

        // ACT
        songsViewModel.getSongs(expectedSongs.map { it.artistId.toString() })

        // ASSERT
        verify(viewStateObserver, times(1)).onChanged(expectedViewState.copy(isLoadingSongs = true))
        verify(viewStateObserver, times(2)).onChanged(expectedViewState.copy(isLoadingSongs = false))

        val commandCaptor = ArgumentCaptor.forClass(SongsViewModel.Command.LoadSongs::class.java)
        verify(commandMock, times(1)).postValue(commandCaptor.capture())

        val triggeredCommand = commandCaptor.firstValue
        assertEquals(expectedLoadSongsCommand.songs, triggeredCommand.songs)
    }
}