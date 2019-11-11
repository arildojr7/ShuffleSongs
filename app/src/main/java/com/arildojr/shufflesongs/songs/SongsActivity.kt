package com.arildojr.shufflesongs.songs

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.arildojr.shufflesongs.R
import com.arildojr.shufflesongs.databinding.ActivitySongsBinding
import com.arildojr.shufflesongs.songs.adapter.SongsAdapter
import com.arildojr.shufflesongs.songs.viewmodel.SongsViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class SongsActivity : AppCompatActivity() {

    private val viewModel: SongsViewModel by viewModel()
    private lateinit var binding: ActivitySongsBinding

    private val songsAdapter by lazy { SongsAdapter(emptyList()){

    } }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_songs)

        setupObservers()
        viewModel.getSongs()

        binding.rvSongs.adapter = songsAdapter
    }

    private fun setupObservers() {
        viewModel.songs.observe(this, Observer {
            Log.e(">>>> ", it.toString())
        })
    }
}
