package com.sm.towatchmovies.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MovieEntity::class], version = 1, exportSchema = false)
abstract class ToWatchMoviesDatabase : RoomDatabase() {
    abstract fun watchlistDao(): WatchlistDao
}
