package com.bryan.laboratoriofinal.data.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class AssetListDto(
    val data: List<AssetDto>,
    val timestamp: Long
)