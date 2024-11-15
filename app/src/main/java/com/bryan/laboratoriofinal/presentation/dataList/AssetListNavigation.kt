package com.bryan.laboratoriofinal.presentation.dataList

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object AssetListDestination

fun NavGraphBuilder.assetList(
    onAssetClick: (String) -> Unit
) {
    composable<AssetListDestination> {
        AssetListRoute(
            onAssetClick = onAssetClick
        )
    }
}