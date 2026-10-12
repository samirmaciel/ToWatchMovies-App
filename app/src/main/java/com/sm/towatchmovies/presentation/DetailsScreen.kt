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

private const val IMAGE = "https://image.tmdb.org/t/p/w500"
@Composable
fun DetailsScreen(
    item: MovieEntity,
    saved: Boolean,
    onSave: () -> Unit,
    onWatched: () -> Unit,
    onBack: () -> Unit
) {
    LazyColumn(Modifier.fillMaxSize()) {
        item {
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(440.dp)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            ) {
                if (item.posterPath != null) AsyncImage(
                    IMAGE + item.posterPath,
                    "Pôster de ${item.title}",
                    Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                Box(
                    Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                listOf(
                                    androidx.compose.ui.graphics.Color.Transparent,
                                    MaterialTheme.colorScheme.background
                                )
                            )
                        )
                )
                TextButton(
                    onClick = onBack,
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(12.dp)
                ) { Text("← Voltar") }
            }
            Column(Modifier.padding(horizontal = 22.dp, vertical = 18.dp)) {
                Text(item.title, style = MaterialTheme.typography.headlineMedium)
                Text(
                    item.originalTitle.ifBlank { item.title },
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    "${item.releaseDate.take(4)}  •  ★ ${"%.1f".format(item.rating)}  •  ${if (item.type == "movie") "Filme" else "Série"}",
                    Modifier.padding(vertical = 12.dp),
                    color = MaterialTheme.colorScheme.tertiary
                )
                Text(
                    item.overview.ifBlank { "Sinopse indisponível." },
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(Modifier.height(22.dp))
                Button(
                    onClick = onSave,
                    Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp)
                ) { Text(if (saved) "✓ Na minha lista" else "Adicionar à minha lista") }
                if (saved) {
                    OutlinedButton(
                        onClick = onWatched,
                        Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Text(if (item.watched) "Marcar como não assistido" else "Marcar como assistido")
                    }
                }
            }
        }
    }
}


