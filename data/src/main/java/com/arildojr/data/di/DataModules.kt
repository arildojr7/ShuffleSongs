package com.arildojr.data.di

import com.arildojr.data.RetrofitInitializer
import com.arildojr.data.songs.SongsRepository
import com.arildojr.data.songs.SongsRepositoryImpl
import com.arildojr.data.songs.datasource.remote.SongsRemoteDataSource
import org.koin.dsl.module


private val apiServiceModule = module {
    single { RetrofitInitializer().songsApiService() }
}

private val repositoryModule = module {
    single<SongsRepository> { SongsRepositoryImpl(get()) }
}

private val dataSourceModule = module {
    single { SongsRemoteDataSource(get()) }
}

fun getDataModules() = listOf(apiServiceModule, repositoryModule, dataSourceModule)