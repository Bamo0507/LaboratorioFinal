package com.bryan.laboratoriofinal.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.bryan.laboratoriofinal.presentation.dataList.AssetListDestination
import com.bryan.laboratoriofinal.presentation.dataList.assetList
import com.bryan.laboratoriofinal.presentation.dataProfile.AssetProfileDestination
import com.bryan.laboratoriofinal.presentation.dataProfile.assetProfile
import com.bryan.laboratoriofinal.presentation.dataProfile.navigateToAssetProfile


@Composable
fun AssetNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = AssetListDestination,
        modifier = modifier
    ) {
        assetList(
            onAssetClick = { assetId ->
                navController.navigateToAssetProfile(
                    destination = AssetProfileDestination(id = assetId)
                )
            }
        )

        assetProfile(
            onBackClick = {
                navController.navigateUp()
            }
        )
    }
}

