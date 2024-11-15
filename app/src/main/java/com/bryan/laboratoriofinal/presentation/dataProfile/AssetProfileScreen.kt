package com.bryan.laboratoriofinal.presentation.dataProfile

import android.os.Bundle
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.material3.IconButtonDefaults.iconButtonColors
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSavedStateRegistryOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.bryan.laboratoriofinal.presentation.common.ErrorLayout
import com.bryan.laboratoriofinal.presentation.common.LoadingLayout
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun AssetProfileRoute(
    onBackClick: () -> Unit,
    id: String,
    owner: SavedStateRegistryOwner = LocalContext.current as SavedStateRegistryOwner
) {
    val context = LocalContext.current
    val owner = LocalSavedStateRegistryOwner.current

    val viewModel: AssetProfileViewModel = viewModel(
        factory = AssetProfileViewModel.provideFactory(
            context = context,
            owner = owner,
            defaultArgs = Bundle().apply { putString("id", id) }
        ) as ViewModelProvider.Factory
    )

    val state by viewModel.state.collectAsState()

    AssetProfileScreen(
        state = state,
        onBackClick = onBackClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssetProfileScreen(
    state: AssetProfileState,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = state.data.name, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(
                        onClick = onBackClick,
                        colors = IconButtonDefaults.iconButtonColors(contentColor = MaterialTheme.colorScheme.onPrimary)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Atrás",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { paddingValues ->
        if (state.isLoading) {
            LoadingLayout(modifier = Modifier.fillMaxSize().padding(paddingValues))
        } else if (state.isError) {
            ErrorLayout(
                onRetryClick = {},
                modifier = Modifier.fillMaxSize().padding(paddingValues)
            )
        } else {
            Spacer(modifier = Modifier.height(8.dp))
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(140.dp)
                        .background(MaterialTheme.colorScheme.secondary, shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = state.data.symbol,
                        style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = "Precio USD:",
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    text = "${"%,.2f".format(state.data.priceUsd.toDoubleOrNull() ?: 0.0)}",
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Cambio 24h:",
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    text = "${state.data.changePercent24Hr}%",
                    style = MaterialTheme.typography.bodyLarge,
                    color = if ((state.data.changePercent24Hr.toDoubleOrNull() ?: 0.0) >= 0) Color(0xFF4CAF50) else Color(0xFFF44336)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Supply:",
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    text = "${"%,.0f".format(state.data.supply?.toDoubleOrNull() ?: 0.0)}",
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Max Supply:",
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    text = "${"%,.0f".format(state.data.maxSupply?.toDoubleOrNull() ?: 0.0)}",
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Market Cap USD:",
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    text = "${"%,.2f".format(state.data.marketCapUsd?.toDoubleOrNull() ?: 0.0)}",
                    style = MaterialTheme.typography.bodyLarge
                )

            }
        }
    }
}
