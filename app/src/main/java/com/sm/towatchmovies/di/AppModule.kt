package com.sm.iwanttowatch.di

import androidx.room.Room
import com.sm.towatchmovies.BuildConfig
import com.sm.towatchmovies.data.MovieRepository
import com.sm.towatchmovies.data.local.ToWatchMoviesDatabase
import com.sm.towatchmovies.data.remote.TmdbApi
import com.sm.towatchmovies.presentation.AppViewModel
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { Room.databaseBuilder(androidContext(), ToWatchMoviesDatabase::class.java, "to-watch-movies.db").build() }
    single { get<ToWatchMoviesDatabase>().watchlistDao() }
    single { Retrofit.Builder().baseUrl("https://api.themoviedb.org/3/").client(OkHttpClient.Builder().build()).addConverterFactory(MoshiConverterFactory.create(Moshi.Builder().add(KotlinJsonAdapterFactory()).build())).build().create(TmdbApi::class.java) }
    single { MovieRepository(get(), get(), BuildConfig.TMDB_API_KEY) }
    viewModel { AppViewModel(get()) }
}
