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
fun SearchScreen(vm: AppViewModel, onOpen: (MovieEntity) -> Unit) {
    var query by remember { mutableStateOf("") }
    val results by vm.results.collectAsState()
    Column(Modifier
        .fillMaxSize()
        .padding(horizontal = 20.dp)) {
        Text(
            "Buscar",
            Modifier.padding(top = 22.dp, bottom = 16.dp),
            style = MaterialTheme.typography.headlineMedium
        )
        OutlinedTextField(
            value = query,
            onValueChange = { query = it; vm.search(it) },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Filmes e séries") },
            singleLine = true,
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.tertiary,
                unfocusedBorderColor = MaterialTheme.colorScheme.outline
            )
        )
        Spacer(Modifier.height(12.dp))
        if (results.isEmpty()) Text(
            "Pesquise no catálogo para encontrar seu próximo título.",
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        LazyColumn { items(results, key = { it.id }) { MediaRow(it, onOpen = onOpen) } }
    }
}


