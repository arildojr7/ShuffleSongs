package com.arildojr.data.songs

import com.arildojr.data.BaseTest
import com.arildojr.data.songs.model.ResponseWrapper
import com.arildojr.data.songs.model.Song
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
class SongsRepositoryImplTest : BaseTest(){

    private lateinit var songsRemoteDataSourceMock: SongsDataSource.Remote
    private lateinit var songsLocalDataSourceMock: SongsDataSource.Local
    private lateinit var songsRepository: SongsRepositoryImpl

    @Before
    fun setUp() {
        songsRemoteDataSourceMock = mock()
        songsLocalDataSourceMock = mock()
        songsRepository = SongsRepositoryImpl(songsLocalDataSourceMock, songsRemoteDataSourceMock)
    }

    @Test
    fun `Get Songs, when it is requested, then return list of songs`() = runBlocking{
        // ARRANGE
        val expectedSongs = createTestData<List<Song>>(loadJsonFromAsset( "songs.json"))
        val expectedLimit = 5

        val expectedSongsResponse = Response.success(ResponseWrapper(results = expectedSongs))

        whenever(songsRemoteDataSourceMock.getSongs(expectedSongs.joinToString(",") { it.artistId.toString() }, expectedLimit))
            .thenReturn(expectedSongsResponse)

        whenever(songsLocalDataSourceMock.getSongs()).thenReturn(
            flow { emit(expectedSongsResponse.body()?.results ?: emptyList()) }
        )

        // ACT
        val response = songsRepository.getSongs(expectedSongs.joinToString(",") { it.artistId.toString() }, expectedLimit)

        // ASSERT
        response.collect {
            assertEquals(expectedSongsResponse.body()?.results,it.body()?.results)
        }
    }
}