package com.bryan.laboratoriofinal.presentation.dataProfile

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute


import kotlinx.serialization.Serializable

@Serializable
data class AssetProfileDestination(
    val id: String
)

fun NavController.navigateToAssetProfile(
    destination: AssetProfileDestination,
    navOptions: NavOptions? = null
) {
    this.navigate(
        destination,
        navOptions
    )}

fun NavGraphBuilder.assetProfile(
    onBackClick: () -> Unit
) {
    composable<AssetProfileDestination> { backStackEntry ->
        val destination: AssetProfileDestination = backStackEntry.toRoute()
        AssetProfileRoute(
            onBackClick = onBackClick,
            id = destination.id
        )
    }
}

