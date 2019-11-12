package com.arildojr.shufflesongs.songs

import android.os.Bundle
import androidx.lifecycle.Observer
import com.arildojr.shufflesongs.R
import com.arildojr.shufflesongs.core.base.BaseActivity
import com.arildojr.shufflesongs.databinding.ActivitySongsBinding
import com.arildojr.shufflesongs.songs.adapter.SongsAdapter
import com.arildojr.shufflesongs.songs.viewmodel.SongsViewModel
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel
import android.view.Menu


class SongsActivity : BaseActivity<ActivitySongsBinding>(R.layout.activity_songs) {

    private val viewModel: SongsViewModel by viewModel()
    private val songsAdapter by lazy {
        SongsAdapter { }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(binding.toolbar)

        binding.rvSongs.adapter = songsAdapter

        launch {
            viewModel.getSongs()
        }

    }

    override fun subscribeUi() {
        viewModel.songs.observe(this, Observer {
            songsAdapter.setData(it)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.songs_menu, menu)
        return true
    }
}
