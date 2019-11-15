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
import com.nhaarman.mockitokotlin2.firstValue
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentCaptor
import retrofit2.Response

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
    fun `Get songs, when it is requested, then LoadSongs command is triggered`() = runBlocking {
        // ARRANGE
        val expectedSongs = createTestData<List<Song>>(loadJsonFromAsset(SONGS_DATA_FILE))
        val expectedSongsCount = 5
        val expectedViewState = SongsViewModel.ViewState()

        songsViewModel.viewState.observeForever(viewStateObserver)

        val expectedLoadSongsCommand = SongsViewModel.Command.LoadSongs(expectedSongs)
        val expectedSongsResponse =
            Response.success(ResponseWrapper(expectedSongsCount, expectedSongs))

        whenever(
            songsRepositoryMock.getSongs(
                expectedSongs.joinToString(",") { it.artistId.toString() },
                expectedSongsCount
            )
        ).thenReturn(flow { emit(expectedSongsResponse) })

        // ACT
        songsViewModel.getSongs(expectedSongs.map { it.artistId.toString() })

        // ASSERT
        verify(viewStateObserver, times(1)).onChanged(expectedViewState.copy(isLoadingSongs = true))
        verify(
            viewStateObserver,
            times(2)
        ).onChanged(expectedViewState.copy(isLoadingSongs = false))

        val commandCaptor = ArgumentCaptor.forClass(SongsViewModel.Command.LoadSongs::class.java)
        verify(commandMock, times(1)).postValue(commandCaptor.capture())

        val triggeredCommand = commandCaptor.firstValue
        assertEquals(expectedLoadSongsCommand.songs, triggeredCommand.songs)
    }

    @Test
    fun `Shuffle songs, when it is requested, then song list is shuffled`() = runBlocking {
        // ARRANGE
        val expectedSongs = createTestData<List<Song>>(loadJsonFromAsset(SONGS_DATA_FILE))
        val expectedLoadSongsCommand = SongsViewModel.Command.LoadSongs(expectedSongs)

        // ACT
        songsViewModel.shuffleSongs(expectedSongs)

        // ASSERT
        val commandCaptor = ArgumentCaptor.forClass(SongsViewModel.Command.LoadSongs::class.java)
        verify(commandMock, times(1)).postValue(commandCaptor.capture())

        val triggeredCommand = commandCaptor.firstValue
        assertNotEquals(expectedLoadSongsCommand.songs, triggeredCommand.songs)

        // check if there are no consecutive artists
        triggeredCommand.songs.forEachIndexed { i, song ->
            assertNotEquals(song.artistId, triggeredCommand.songs.getOrNull(i + 1)?.artistId)
        }
    }

}