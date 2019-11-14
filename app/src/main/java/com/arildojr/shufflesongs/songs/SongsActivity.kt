package com.arildojr.shufflesongs.songs

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import com.arildojr.shufflesongs.R
import com.arildojr.shufflesongs.core.base.BaseActivity
import com.arildojr.shufflesongs.databinding.ActivitySongsBinding
import com.arildojr.shufflesongs.songs.adapter.SongsAdapter
import com.arildojr.shufflesongs.songs.viewmodel.SongsViewModel
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel

class SongsActivity : BaseActivity<ActivitySongsBinding>(R.layout.activity_songs) {

    private val viewModel: SongsViewModel by viewModel()
    private val songsAdapter by lazy { SongsAdapter() }
    private val artistIds = listOf("909253", "1171421960", "358714030", "1419227", "264111789")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(binding.toolbar)

        binding.viewModel = viewModel
        binding.rvSongs.adapter = songsAdapter

        launch {
            viewModel.getSongs(artistIds)
        }
    }

    override fun subscribeUi() {
        viewModel.viewState.observe(this, Observer {
            render(it)
        })
        viewModel.command.observe(this, Observer { command ->
            when (command) {
                is SongsViewModel.Command.LoadSongs -> {
                    songsAdapter.setData(command.songs)
                }
                is SongsViewModel.Command.ErrorOnLoadSongs -> {
                    showErrorDialog()
                }

            }
        })
    }

    private fun showErrorDialog() {
        AlertDialog.Builder(this@SongsActivity)
            .setTitle(getString(R.string.error_dialog_title))
            .setMessage(getString(R.string.error_dialog_description))
            .setPositiveButton(getString(R.string.error_dialog_try_again)) { _, _ ->
                launch {
                    viewModel.getSongs(artistIds)
                }
            }
            .setNegativeButton(getString(R.string.error_dialog_exit)) { _, _ ->
                finish()
            }
            .show()
    }

    private fun render(viewState: SongsViewModel.ViewState) {
        when (viewState.isLoadingSongs) {
            true -> {
                binding.pbLoaderSongs.visibility = View.VISIBLE
                binding.toolbar.visibility = View.GONE
            }
            false -> {
                binding.pbLoaderSongs.visibility = View.GONE
                binding.toolbar.visibility = View.VISIBLE
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.songs_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.btnShuffle -> {
                viewModel.shuffleSongs()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
