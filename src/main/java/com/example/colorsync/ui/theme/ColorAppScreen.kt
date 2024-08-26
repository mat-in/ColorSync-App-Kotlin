package com.example.colorsync.ui.theme

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.colorsync.data.ColorEntry

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColorAppScreen(
    colorList: List<ColorEntry>,
    pendingSyncCount: Int,
    onAddColorClick: () -> Unit,
    onSyncClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("ColorSync") },
                actions = {
                    IconButton(onClick = onSyncClick) {
                        Icon(Icons.Filled.Refresh, contentDescription = "Sync")
                        Text(
                            text = "$pendingSyncCount",
                            color = Color.Blue,
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(end = 30.dp),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis)
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddColorClick) {
                Icon(Icons.Filled.Add, contentDescription = "Add Color")
            }
        },
        content = { paddingValues ->
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 150.dp),
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(8.dp),
                content = {
                    items(colorList) { colorEntry ->
                        ColorCard(colorEntry)
                    }
                }
            )
        }
    )
}

@Composable
fun ColorCard(colorEntry: ColorEntry) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(android.graphics.Color.parseColor(colorEntry.colorHex))
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = colorEntry.colorHex,
                style = MaterialTheme.typography.titleLarge,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = colorEntry.getFormattedDate(),
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White
            )
        }
    }
}
