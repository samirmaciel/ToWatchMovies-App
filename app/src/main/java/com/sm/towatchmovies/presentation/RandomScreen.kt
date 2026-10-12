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
fun RandomScreen(items: List<MovieEntity>, onOpen: (MovieEntity) -> Unit) {
    var selected by remember { mutableStateOf<MovieEntity?>(null) }
    Column(
        Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("O que assistir?", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(24.dp))
        selected?.let {
            Poster(it.posterPath, Modifier.size(220.dp, 320.dp))
            Text(
                it.title,
                Modifier.padding(top = 16.dp),
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                "★ ${"%.1f".format(it.rating)}  •  ${it.releaseDate.take(4)}  •  ${if (it.type == "movie") "Filme" else "Série"}",
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            TextButton(onClick = { onOpen(it) }) { Text("Ver detalhes") }
        } ?: EmptyCard(
            "Sua próxima sessão",
            "Adicione títulos à sua lista e deixe a sorte escolher."
        )
        Spacer(Modifier.height(18.dp))
        Button(onClick = { selected = items.randomOrNull() }, enabled = items.isNotEmpty()) {
            Text(if (selected == null) "Sortear" else "Sortear novamente")
        }
        if (items.isEmpty()) Text(
            "Adicione títulos à sua lista para começar.",
            Modifier.padding(top = 10.dp),
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}


