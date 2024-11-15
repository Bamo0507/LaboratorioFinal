package com.bryan.laboratoriofinal.data.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class AssetProfileDto(
    val data: AssetDto,
    val timestamp: Long
)