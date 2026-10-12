package com.sm.towatchmovies.presentation.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.sm.towatchmovies.data.local.MovieEntity
import com.sm.towatchmovies.presentation.HomeState

private const val IMAGE = "https://image.tmdb.org/t/p/w500"

@Composable
fun FeaturedCard(item: MovieEntity, onClick: () -> Unit) {
    Column(Modifier
        .width(226.dp)
        .clickable(onClick = onClick)) {
        Box(
            Modifier
                .fillMaxWidth()
                .height(326.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant)
        ) {
            if (item.posterPath != null) AsyncImage(
                model = IMAGE + item.posterPath,
                contentDescription = "Poster de ${item.title}",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            ) else PlaceholderPoster(Modifier.fillMaxSize())
            Box(
                Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                androidx.compose.ui.graphics.Color.Transparent,
                                androidx.compose.ui.graphics.Color(0xE8000000)
                            ), startY = 210f
                        )
                    )
            )
            Column(
                Modifier
                    .align(Alignment.BottomStart)
                    .padding(horizontal = 16.dp, vertical = 17.dp)
            ) {
                Text(
                    if (item.type == "movie") "FILME" else "SÉRIE",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.82f),
                    letterSpacing = 1.5.sp
                )
                Spacer(Modifier.height(5.dp))
                Text(
                    item.title,
                    maxLines = 2,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(Modifier.height(7.dp))
                Text(
                    "★  ${"%.1f".format(item.rating)}     ${item.releaseDate.take(4)}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.82f)
                )
            }
        }
    }
}

@Composable
fun LoadingCard() {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .clip(RoundedCornerShape(18.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(18.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CircularProgressIndicator(
            Modifier.size(22.dp),
            strokeWidth = 2.dp,
            color = MaterialTheme.colorScheme.tertiary
        )
        Spacer(Modifier.width(14.dp))
        Text(
            "Carregando recomendações¦",
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun ApiPrompt(state: HomeState, retry: () -> Unit) {
    EmptyCard(
        if (state.error != null) "Não foi possível carregar" else "Catálogo indisponível",
        if (state.error != null) "Verifique sua conexão e tente novamente." else "Configure TMDB_API_KEY em gradle.properties para descobrir filmes e séries.",
        retry,
        actionLabel = "Tentar novamente"
    )
}

@Composable
fun SectionTitle(title: String, trailing: String?) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(title, style = MaterialTheme.typography.titleLarge)
        trailing?.let {
            Text(
                it,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun PosterCard(item: MovieEntity, onOpen: (MovieEntity) -> Unit) {
    Column(Modifier
        .width(132.dp)
        .clickable { onOpen(item) }) {
        Poster(item.posterPath, Modifier
            .fillMaxWidth()
            .height(190.dp))
        Spacer(Modifier.height(9.dp))
        Text(
            item.title,
            maxLines = 1,
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            "★ ${"%.1f".format(item.rating)}  •  ${item.releaseDate.take(4)}",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun Poster(path: String?, modifier: Modifier = Modifier) {
    Box(
        modifier
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant),
        contentAlignment = Alignment.Center
    ) {
        if (path != null) AsyncImage(
            model = IMAGE + path,
            contentDescription = "Poster",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        else PlaceholderPoster(Modifier.fillMaxSize())
    }
}

@Composable
fun PlaceholderPoster(modifier: Modifier = Modifier) {
    Box(
        modifier.background(
            Brush.verticalGradient(
                listOf(
                    MaterialTheme.colorScheme.surfaceVariant,
                    MaterialTheme.colorScheme.surface
                )
            )
        ), contentAlignment = Alignment.Center
    ) {
        Text(
            "▶",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun EmptyCard(
    title: String,
    body: String,
    action: (() -> Unit)? = null,
    actionLabel: String = ""
) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(20.dp)
    ) {
        Text(title, fontWeight = FontWeight.SemiBold, style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(5.dp))
        Text(
            body,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.bodyMedium
        )
        if (action != null) {
            Spacer(Modifier.height(10.dp))
            TextButton(
                onClick = action,
                contentPadding = PaddingValues(0.dp)
            ) {
                Text(
                    actionLabel.ifBlank { "Explorar catalogo" },
                    color = MaterialTheme.colorScheme.tertiary
                )
            }
        }
    }
}

@Composable
fun FilterRow(options: List<String>, selected: String, onSelect: (String) -> Unit) {
    Row(
        Modifier.horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        options.forEach { option ->
            FilterChip(
                selected == option,
                { onSelect(option) },
                label = { Text(option) })
        }
    }
}

@Composable
fun MediaRow(
    item: MovieEntity,
    onOpen: (MovieEntity) -> Unit,
    onWatched: ((MovieEntity, Boolean) -> Unit)? = null
) {
    Row(
        Modifier
            .fillMaxWidth()
            .clickable { onOpen(item) }
            .padding(vertical = 9.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Poster(item.posterPath, Modifier.size(72.dp, 104.dp))
        Column(Modifier
            .weight(1f)
            .padding(start = 14.dp)) {
            Text(
                item.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                "${item.releaseDate.take(4)}  •  ★ ${"%.1f".format(item.rating)}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                if (item.type == "movie") "Filme" else "Série",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.tertiary
            )
            if (onWatched != null) TextButton(
                onClick = { onWatched(item, !item.watched) },
                contentPadding = PaddingValues(horizontal = 0.dp)
            ) {
                Text(if (item.watched) "✓ Assistido" else "Marcar como assistido")
            }
        }
    }
}

