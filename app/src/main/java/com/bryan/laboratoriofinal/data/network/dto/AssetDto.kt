package com.bryan.laboratoriofinal.data.network.dto

import com.bryan.laboratoriofinal.domain.model.Asset
import kotlinx.serialization.Serializable

//Declarar DTO con serializable junto con toda la info que manejaré
//No es necesario delcarla todo, esto ya se maneja de manera correcta en el client factory para que le valga lo que no ponga en las model classes
//Aquí únicamente manejamos la info que viene, y cómo la convertiremos a algo que entienda compose

@Serializable
data class AssetDto(
    val id: String,
    val rank: String,
    val symbol: String,
    val name: String,
    val supply: String?,
    val maxSupply: String?,
    val marketCapUsd: String?,
    val volumeUsd24Hr: String?,
    val priceUsd: String,
    val changePercent24Hr: String?,
    val vwap24Hr: String?,
    val explorer: String?
)

//Declarar el método para que luego lo que venga del DTO se pueda mapear a mi Model class
fun AssetDto.mapToAssetModel(timestamp: Long): Asset {
    return Asset(
        id = id,
        rank = rank,
        symbol = symbol,
        name = name,
        priceUsd = priceUsd,
        changePercent24Hr = changePercent24Hr ?: "0.0",
        supply = supply,
        maxSupply = maxSupply,
        marketCapUsd = marketCapUsd,
        timestamp = timestamp
    )
}

