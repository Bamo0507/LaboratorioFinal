package com.bryan.laboratoriofinal.presentation.dataList

import com.bryan.laboratoriofinal.domain.model.Asset

data class AssetListState(
    val isLoading: Boolean = true,
    val data: List<Asset> = emptyList(),
    val isError: Boolean = false,
    val isConnected: Boolean = true,
    val lastSaveTime: Long? = null
)