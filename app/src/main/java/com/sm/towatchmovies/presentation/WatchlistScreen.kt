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
fun WatchlistScreen(
    items: List<MovieEntity>,
    onOpen: (MovieEntity) -> Unit,
    onWatched: (MovieEntity, Boolean) -> Unit
) {
    var type by remember { mutableStateOf("Todos") }
    var status by remember { mutableStateOf("Todos") }
    Column(Modifier
        .fillMaxSize()
        .padding(horizontal = 20.dp)) {
        Text(
            "Minha lista",
            Modifier.padding(top = 22.dp, bottom = 10.dp),
            style = MaterialTheme.typography.headlineMedium
        )
        FilterRow(listOf("Todos", "Filmes", "Séries"), type) { type = it }
        Spacer(Modifier.height(6.dp))
        FilterRow(listOf("Todos", "Não assistidos", "Assistidos"), status) { status = it }
        val visible = items.filter {
            (type == "Todos" || (type == "Filmes") == (it.type == "movie")) &&
                    (status == "Todos" || (status == "Assistidos") == it.watched)
        }
        if (visible.isEmpty()) EmptyCard(
            "Sua lista está vazia",
            "Adicione filmes e séries que você quer assistir."
        )
        else LazyColumn { items(visible, key = { it.id }) { MediaRow(it, onOpen, onWatched) } }
    }
}


