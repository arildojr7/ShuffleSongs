package com.arildojr.shufflesongs.songs

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.arildojr.shufflesongs.BaseTest
import com.arildojr.data.songs.model.Song
import com.arildojr.shufflesongs.songs.adapter.SongsAdapter
import kotlinx.android.synthetic.main.item_song_list.view.*
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.android.controller.ActivityController
import org.robolectric.annotation.Config

@Config(sdk = [Build.VERSION_CODES.O_MR1])
@RunWith(RobolectricTestRunner::class)
class SongsActivityTest : BaseTest() {

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    private lateinit var activity: SongsActivity
    private lateinit var activityController: ActivityController<SongsActivity>
    private val SONGS_DATA_FILE = "songs.json"

    @Before
    fun setUp() {
        activityController = Robolectric.buildActivity(SongsActivity::class.java)
        activity = activityController.get()
        activityController.create().start().visible()
    }

    @Test
    fun `load songs, when set data in adapter, then show song list`() {
        // ARRANGE
        val expectedSongs = createTestData<List<Song>>(loadJsonFromAsset(SONGS_DATA_FILE))
        val recycler = activity.binding.rvSongs

        val adapter = SongsAdapter()
        recycler.adapter = adapter

        // ACT
        adapter.setData(expectedSongs)
        recycler.update()

        // ASSERT
        expectedSongs.forEachIndexed { i, song ->
            val songTitle = recycler.findViewHolderForAdapterPosition(i)?.itemView?.tvSongTitle?.text
            val artistName = recycler.findViewHolderForAdapterPosition(i)?.itemView?.tvArtistName?.text

            assertEquals(song.trackName, songTitle)
            assertEquals("${song.artistName} (${song.primaryGenreName})", artistName)
        }
    }
}