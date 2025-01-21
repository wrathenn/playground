package com.wrathenn.fieldhash.desktop.view

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.ui.unit.dp
import com.wrathenn.fieldhash.api.model.NamedDto
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import com.wrathenn.fieldhash.desktop.navigation.NavController
import com.wrathenn.fieldhash.desktop.vm.PartitionChainListViewModel

@Composable
fun PartitionChainsView(
    viewModel: PartitionChainListViewModel,
    navController: NavController,
    onEntityClick: (NamedDto<Long>) -> Unit = {},
    onAddNewEntity: () -> Unit = {}
) {
    val entities by remember { derivedStateOf { viewModel.items } }

    LaunchedEffect(Unit) {
        viewModel.fetchItems()
    }

    LazyVerticalGrid(
        columns = GridCells.FixedSize(100.dp),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp, alignment = BiasAlignment.Horizontal(0.5F)),
        verticalArrangement = Arrangement.spacedBy(16.dp, alignment = BiasAlignment.Vertical(0.5F))
    ) {
        items(entities, key = { it.id }) { entity ->
            PartitionChainBar(entity = entity, onClick = { onEntityClick(entity) })
        }
        item {
            PlusBar(onClick = onAddNewEntity)
        }
    }
}

@Composable
fun PartitionChainBar(entity: NamedDto<Long>, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .width(100.dp)
            .height(100.dp)
            .clickable(onClick = onClick),
        elevation = 4.dp
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(text = entity.name, style = MaterialTheme.typography.h6)
        }
    }
}

@Composable
fun PlusBar(onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .clickable(onClick = onClick),
        elevation = 4.dp,
        backgroundColor = Color.LightGray
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(text = "+ Add New", style = MaterialTheme.typography.h6)
        }
    }
}
