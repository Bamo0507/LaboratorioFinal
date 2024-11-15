package com.bryan.laboratoriofinal.domain.model

//Aquí declaro toda la info que SÍ voy a manejar de lo que me devuelve el endpoint
data class Asset (
    val id: String,
    val rank: String,
    val symbol: String,
    val name: String,
    val priceUsd: String,
    val changePercent24Hr: String,
    val supply: String? = null,
    val maxSupply: String? = null,
    val marketCapUsd: String? = null,
    val timestamp: Long? = null
)