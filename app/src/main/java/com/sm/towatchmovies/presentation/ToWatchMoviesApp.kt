package com.sm.towatchmovies.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import org.koin.androidx.compose.koinViewModel

private val tabs = listOf("Início", "Buscar", "Minha lista", "Sortear")

@Composable
fun ToWatchMoviesApp(vm: AppViewModel = koinViewModel()) {
    val nav = rememberNavController()
    val state by vm.state.collectAsState()
    val route by nav.currentBackStackEntryAsState()
    val current = route?.destination?.route

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            if (current in tabs) NavigationBar(
                containerColor = MaterialTheme.colorScheme.background,
                contentColor = MaterialTheme.colorScheme.onSurface,
                tonalElevation = 0.dp
            ) {
                val glyphs = listOf("⌂", "⌕", "▤", "↻")
                tabs.forEachIndexed { index, label ->
                    NavigationBarItem(
                        selected = current == label,
                        onClick = {
                            nav.navigate(label) {
                                launchSingleTop = true
                                restoreState = true
                                popUpTo(nav.graph.startDestinationId) { saveState = true }
                            }
                        },
                        icon = { Text(glyphs[index], style = MaterialTheme.typography.titleLarge) },
                        label = { Text(label) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.onSurface,
                            selectedTextColor = MaterialTheme.colorScheme.onSurface,
                            indicatorColor = MaterialTheme.colorScheme.surfaceVariant,
                            unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                }
            }
        }
    ) { padding ->
        NavHost(
            navController = nav,
            startDestination = "Início",
            modifier = Modifier.padding(padding)
        ) {
            composable("Início") {
                HomeScreen(
                    state = state,
                    onOpen = { nav.navigate("details/${it.id}") },
                    onSearch = { nav.navigate("Buscar") },
                    onSettings = { nav.navigate("settings") },
                    onRefresh = vm::refresh
                )
            }
            composable("Buscar") { SearchScreen(vm, onOpen = { nav.navigate("details/${it.id}") }) }
            composable("Minha lista") {
                WatchlistScreen(
                    state.watchlist,
                    onOpen = { nav.navigate("details/${it.id}") },
                    onWatched = vm::watched
                )
            }
            composable("Sortear") {
                RandomScreen(
                    state.watchlist,
                    onOpen = { nav.navigate("details/${it.id}") })
            }
            composable("settings") {
                SimplePage(
                    "Configurações",
                    "Tema: escuro\nIdioma: Português\nRegião: Brasil\n\nWantToWatch • versão 1.0"
                )
            }
            composable(
                "details/{id}",
                arguments = listOf(navArgument("id") { type = NavType.StringType })
            ) { entry ->
                val id = entry.arguments?.getString("id") ?: ""
                val savedItem = state.watchlist.firstOrNull { it.id == id }
                val catalogItem = savedItem ?: vm.findById(id)
                val loadedDetails by vm.details.collectAsState()
                LaunchedEffect(id) { catalogItem?.let(vm::loadDetails) }
                val item = loadedDetails?.takeIf { it.id == id } ?: catalogItem
                if (item != null) DetailsScreen(
                    item,
                    saved = savedItem != null,
                    onSave = { vm.toggle(item) },
                    onWatched = { vm.watched(item, !item.watched) },
                    onBack = { nav.popBackStack() }
                ) else SimplePage(
                    "Detalhes",
                    "Não foi possível carregar este título. Tente novamente pela busca ou pelo catálogo.",
                    onBack = { nav.popBackStack() }
                )
            }
        }
    }
}


