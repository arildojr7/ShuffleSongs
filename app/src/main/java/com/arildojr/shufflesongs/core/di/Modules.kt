package com.arildojr.shufflesongs.core.di

import com.arildojr.shufflesongs.songs.viewmodel.SongsViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

private val songsModules = module {
    viewModel { SongsViewModel(get()) }
}

fun getSongsModules() = listOf(songsModules)
