package com.arildojr.data.di

import com.arildojr.data.RetrofitInitializer
import com.arildojr.data.songs.SongsDataSource
import com.arildojr.data.songs.SongsRepository
import com.arildojr.data.songs.SongsRepositoryImpl
import com.arildojr.data.songs.datasource.local.SongsLocalDataSource
import com.arildojr.data.songs.datasource.local.database.SongsDatabase
import com.arildojr.data.songs.datasource.remote.SongsRemoteDataSource
import org.koin.dsl.module

private val dbModule = module {
    single { SongsDatabase(get()) }
    single { get<SongsDatabase>().songDao() }
}

private val apiServiceModule = module {
    single { RetrofitInitializer().songsApiService() }
}

private val repositoryModule = module {
    single<SongsRepository> { SongsRepositoryImpl(get(),get()) }
}

private val dataSourceModule = module {
    single<SongsDataSource.Local> { SongsLocalDataSource(get()) }
    single<SongsDataSource.Remote> { SongsRemoteDataSource(get()) }
}

fun getDataModules() = listOf(apiServiceModule, repositoryModule, dataSourceModule, dbModule)