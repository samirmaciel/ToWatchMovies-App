package com.sm.towatchmovies.data

import com.sm.towatchmovies.data.local.MovieEntity
import com.sm.towatchmovies.data.local.WatchlistDao
import com.sm.towatchmovies.data.remote.TmdbApi
import com.sm.towatchmovies.data.remote.TmdbTitle
import kotlinx.coroutines.flow.Flow

class MovieRepository(private val dao: WatchlistDao, private val api: TmdbApi, private val apiKey: String) {
    val watchlist: Flow<List<MovieEntity>> = dao.observeAll()
    suspend fun toggle(movie: MovieEntity) { if (dao.find(movie.id) == null) dao.insert(movie) else dao.delete(movie.id) }
    suspend fun setWatched(id: String, watched: Boolean) = dao.setWatched(id, watched)
    suspend fun trending() = if (apiKey.isBlank()) emptyList() else api.trending(apiKey).results.map { it.toEntity() }
    suspend fun topRated() = if (apiKey.isBlank()) emptyList() else (api.topMovies(apiKey).results + api.topShows(apiKey).results).map { it.toEntity() }.sortedByDescending { it.rating }
    suspend fun search(query: String) = if (apiKey.isBlank() || query.isBlank()) emptyList() else api.search(apiKey, query).results.filter { it.media_type == "movie" || it.media_type == "tv" }.map { it.toEntity() }
    suspend fun details(movie: MovieEntity): MovieEntity? {
        if (apiKey.isBlank()) return null
        val result = api.details(movie.type, movie.apiId, apiKey)
        val localizedPoster = result.images?.posters
            ?.firstOrNull { it.iso_639_1 == "pt" }
            ?.file_path
        return movie.copy(
            title = result.title ?: result.name ?: movie.title,
            originalTitle = result.original_title ?: result.original_name ?: movie.originalTitle,
            posterPath = localizedPoster ?: result.poster_path ?: movie.posterPath,
            overview = result.overview ?: movie.overview,
            rating = result.vote_average ?: movie.rating,
            releaseDate = result.release_date ?: result.first_air_date ?: movie.releaseDate
        )
    }
}

private fun TmdbTitle.toEntity() = MovieEntity(
    id = "${media_type ?: if (name != null) "tv" else "movie"}-$id", apiId = id,
    title = title ?: name ?: "Sem título", originalTitle = original_title ?: original_name ?: "",
    posterPath = poster_path, overview = overview.orEmpty(), rating = vote_average ?: 0.0,
    releaseDate = release_date ?: first_air_date.orEmpty(), type = media_type ?: if (name != null) "tv" else "movie"
)
