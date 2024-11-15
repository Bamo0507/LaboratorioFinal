package com.bryan.laboratoriofinal.presentation.dataList

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.material3.IconButtonDefaults.iconButtonColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bryan.laboratoriofinal.R
import com.bryan.laboratoriofinal.domain.model.Asset
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun AssetListRoute(
    onAssetClick: (String) -> Unit,
    viewModel: AssetListViewModel = viewModel(
        factory = AssetListViewModel.provideFactory(LocalContext.current)
    )
) {
    val state by viewModel.state.collectAsState()

    AssetListScreen(
        state = state,
        onRetryClick = { viewModel.getAssets() },
        onAssetClick = onAssetClick,
        onSaveOfflineClick = { viewModel.saveDataOffline() }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AssetListScreen(
    state: AssetListState,
    onRetryClick: () -> Unit,
    onAssetClick: (String) -> Unit,
    onSaveOfflineClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isConnected = state.isConnected

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Assets", fontWeight = FontWeight.Bold)
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text("Ver offline") },
                icon = {
                    Icon(
                        imageVector = Icons.Default.Save,
                        contentDescription = "Guardar offline"
                    )
                },
                onClick = onSaveOfflineClick
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            if (!isConnected) {
                // Mostrar banner indicando que el usuario está sin conexión
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.LightGray)
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = "Offline",
                        tint = Color.Red,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Estás sin conexión. Mostrando datos locales.",
                        color = Color.Red,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            if (state.lastSaveTime != null) {
                val dateTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                    .format(Date(state.lastSaveTime))
                Box(modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.surfaceVariant),
                    contentAlignment = Alignment.Center){
                    Text(
                        text = "Datos guardados el: $dateTime",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }

            when {
                state.isLoading -> {
                    LoadingLayout(modifier = Modifier.fillMaxSize())
                }
                state.isError -> {
                    ErrorLayout(
                        onRetryClick = onRetryClick,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                else -> {
                    AssetListContent(
                        assets = state.data,
                        onAssetClick = onAssetClick
                    )
                }
            }
        }
    }
}

@Composable
private fun AssetListContent(
    assets: List<Asset>,
    onAssetClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        items(assets) { asset ->
            AssetCard(
                asset = asset,
                onClick = { onAssetClick(asset.id) }
            )
        }
    }
}

@Composable
private fun AssetCard(
    asset: Asset,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.surfaceVariant),
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(MaterialTheme.colorScheme.secondary, shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = asset.symbol,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = asset.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Precio USD: ${"%,.2f".format(asset.priceUsd.toDoubleOrNull() ?: 0.0)}",
                    style = MaterialTheme.typography.bodySmall
                )

                Text(
                    text = "Cambio 24h: ${asset.changePercent24Hr}%",
                    style = MaterialTheme.typography.bodySmall,
                    color = if ((asset.changePercent24Hr.toDoubleOrNull() ?: 0.0) >= 0) Color(0xFF4CAF50) else Color(0xFFF44336),
                    fontWeight = FontWeight.SemiBold
                )
            }

            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}


@Composable
private fun LoadingLayout(
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun ErrorLayout(
    onRetryClick: () -> Unit,
    message: String = stringResource(R.string.error_fetching_data),
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            Icons.Default.Warning,
            contentDescription = "Error",
            tint = Color.Red,
            modifier = Modifier.size(32.dp)
        )
        Text(message)
        OutlinedButton(onClick = onRetryClick) {
            Text(text = stringResource(R.string.retry))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AssetCardPreview() {
    val asset = Asset(
        id = "bitcoin",
        rank = "1",
        symbol = "BTC",
        name = "Bitcoin",
        priceUsd = "50000.00",
        changePercent24Hr = "2.5",
        timestamp = System.currentTimeMillis()
    )
    AssetCard(asset = asset, onClick = {})
}
