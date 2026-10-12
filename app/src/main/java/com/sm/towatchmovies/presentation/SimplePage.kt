package com.sm.towatchmovies.presentation

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
fun SimplePage(title: String, body: String, onBack: (() -> Unit)? = null) {
    Column(Modifier
        .fillMaxSize()
        .padding(24.dp)) {
        onBack?.let { TextButton(onClick = it) { Text("← Voltar") } }
        Text(title, style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(16.dp))
        Text(body, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}
