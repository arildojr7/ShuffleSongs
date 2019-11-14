package com.arildojr.shufflesongs.core.di

import com.arildojr.shufflesongs.core.util.CommandInjector
import com.arildojr.shufflesongs.core.util.CommandProvider
import com.arildojr.shufflesongs.songs.viewmodel.SongsViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

private val songsModules = module {
    viewModel { SongsViewModel(get(), get()) }
}

private val commandInjector = module(override = true) {
    single<CommandProvider> { CommandInjector }
}

fun getSongsModules() = listOf(songsModules, commandInjector)
