package com.sm.towatchmovies.presentation

import com.sm.towatchmovies.presentation.components.*

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
@Composable
fun HomeScreen(
    state: HomeState,
    onOpen: (MovieEntity) -> Unit,
    onSearch: () -> Unit,
    onSettings: () -> Unit,
    onRefresh: () -> Unit
) {
    var filter by remember { mutableStateOf("Em alta") }
    val filters = listOf("Em alta", "Bem avaliados", "Filmes", "Séries", "Minha lista")
    val source = when (filter) {
        "Bem avaliados" -> state.topRated
        "Minha lista" -> state.watchlist
        else -> state.trending
    }
    val featured = when (filter) {
        "Filmes" -> (state.trending + state.topRated).distinctBy { it.id }
            .filter { it.type == "movie" }

        "Séries" -> (state.trending + state.topRated).distinctBy { it.id }
            .filter { it.type == "tv" }

        else -> source
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {
        item {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(start = 22.dp, end = 20.dp, top = 18.dp, bottom = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        "WANT TO WATCH",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.tertiary,
                        letterSpacing = 1.8.sp
                    )
                    Spacer(Modifier.height(5.dp))
                    Text(
                        "Olá, cinéfilo",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        "Seu próximo favorito está aqui",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Box(
                    Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .clickable(onClick = onSettings),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "WW",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
        item {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .clip(RoundedCornerShape(17.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .clickable(onClick = onSearch)
                    .padding(horizontal = 16.dp, vertical = 15.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "⌕",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(Modifier.width(12.dp))
                Text(
                    "Buscar filmes e séries",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(Modifier.weight(1f))
                Text(
                    "⌖",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        item {
            Text(
                "Descubra por",
                Modifier.padding(start = 22.dp, end = 20.dp, top = 26.dp, bottom = 12.dp),
                style = MaterialTheme.typography.titleMedium
            )
            Row(
                Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(9.dp)
            ) {
                filters.forEach { option ->
                    val selected = filter == option
                    Surface(
                        onClick = { filter = option },
                        shape = RoundedCornerShape(50),
                        color = if (selected) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.surfaceVariant,
                        contentColor = if (selected) MaterialTheme.colorScheme.background else MaterialTheme.colorScheme.onSurfaceVariant
                    ) {
                        Text(
                            option,
                            Modifier.padding(horizontal = 15.dp, vertical = 10.dp),
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                }
            }
        }
        item {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(start = 22.dp, end = 20.dp, top = 29.dp, bottom = 14.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    if (filter == "Minha lista") "Quero assistir" else "Em destaque",
                    style = MaterialTheme.typography.titleLarge
                )
                if (filter == "Minha lista") Text(
                    "${state.watchlist.size} títulos",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        if (featured.isEmpty()) {
            item {
                if (filter == "Minha lista") EmptyCard(
                    "Sua lista começa aqui",
                    "Busque filmes e séries para guardar tudo o que quer assistir."
                )
                else if (state.loading) LoadingCard()
                else ApiPrompt(state, onRefresh)
            }
        } else {
            item {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 26.dp),
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    items(featured.take(12), key = { it.id }) { item ->
                        FeaturedCard(
                            item,
                            onClick = { onOpen(item) })
                    }
                }
            }
        }
        if (state.watchlist.isNotEmpty() && filter != "Minha lista") {
            item { SectionTitle("Na sua lista", "${state.watchlist.size} títulos") }
            item {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(state.watchlist.take(10), key = { it.id }) { PosterCard(it, onOpen) }
                }
            }
        }
        if (filter != "Bem avaliados" && filter != "Minha lista" && filter != "Em alta") {
            item { SectionTitle("Mais bem avaliados", null) }
            if (state.topRated.isEmpty()) item { if (!state.loading) ApiPrompt(state, onRefresh) }
            else item {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(state.topRated.take(10), key = { it.id }) { PosterCard(it, onOpen) }
                }
            }
        }
        if (state.error != null && featured.isNotEmpty()) {
            item {
                TextButton(
                    onClick = onRefresh,
                    modifier = Modifier.fillMaxWidth()
                ) { Text("Atualizar catálogo") }
            }
        }
    }
}


