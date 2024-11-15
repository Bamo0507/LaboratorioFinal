package com.bryan.laboratoriofinal.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bryan.laboratoriofinal.domain.model.Asset

@Entity(tableName = "assets")
data class AssetEntity(
    @PrimaryKey val id: String,
    val rank: String,
    val symbol: String,
    val name: String,
    val priceUsd: String,
    val changePercent24Hr: String,
    val supply: String?,
    val maxSupply: String?,
    val marketCapUsd: String?,
    val timestamp: Long
)

// Funciones de mapeo
fun AssetEntity.toDomainModel(): Asset {
    return Asset(
        id = id,
        rank = rank,
        symbol = symbol,
        name = name,
        priceUsd = priceUsd,
        changePercent24Hr = changePercent24Hr,
        supply = supply,
        maxSupply = maxSupply,
        marketCapUsd = marketCapUsd,
        timestamp = timestamp
    )
}

fun Asset.toEntity(): AssetEntity {
    return AssetEntity(
        id = id,
        rank = rank,
        symbol = symbol,
        name = name,
        priceUsd = priceUsd,
        changePercent24Hr = changePercent24Hr,
        supply = supply,
        maxSupply = maxSupply,
        marketCapUsd = marketCapUsd,
        timestamp = timestamp ?: System.currentTimeMillis()
    )
}
