package com.bryan.laboratoriofinal.presentation.dataProfile

import com.bryan.laboratoriofinal.domain.model.Asset

data class AssetProfileState(
    val isLoading: Boolean = false,
    val data: Asset = Asset(
        id = "",
        rank = "",
        symbol = "",
        name = "",
        priceUsd = "",
        changePercent24Hr = ""
    ),
    val isError: Boolean = false,
    val isConnected: Boolean = false
)
