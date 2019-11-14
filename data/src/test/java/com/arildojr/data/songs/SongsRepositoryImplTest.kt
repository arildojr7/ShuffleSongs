package com.arildojr.data.songs

import com.arildojr.data.BaseTest
import com.arildojr.data.songs.model.ResponseWrapper
import com.arildojr.data.songs.model.Song
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class SongsRepositoryImplTest : BaseTest(){

    private lateinit var songsRemoteDataSourceMock: SongsDataSource.Remote
    private lateinit var songsRepository: SongsRepositoryImpl

    @Before
    fun setUp() {
        songsRemoteDataSourceMock = mock()
        songsRepository = SongsRepositoryImpl(songsRemoteDataSourceMock)
    }

    @Test
    fun `Get Songs, when it is requested, then return list of songs`() = runBlocking{
        // ARRANGE
        val expectedSongs = createTestData<List<Song>>(loadJsonFromAsset( "songs.json"))
        val expectedLimit = 5

        val expectedSongsResponse = Response.success(ResponseWrapper(results = expectedSongs))

        whenever(songsRemoteDataSourceMock.getSongs(expectedSongs.joinToString(",") { it.artistId.toString() }, expectedLimit))
            .thenReturn(expectedSongsResponse)

        // ACT
        val response = songsRepository.getSongs(expectedSongs.joinToString(",") { it.artistId.toString() }, expectedLimit)

        // ASSERT
        assertEquals(expectedSongsResponse, response)
    }
}