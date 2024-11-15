package com.bryan.laboratoriofinal.domain.network

import com.bryan.laboratoriofinal.data.network.dto.AssetListDto
import com.bryan.laboratoriofinal.data.network.dto.AssetProfileDto
import com.bryan.laboratoriofinal.domain.network.util.Result
import com.bryan.laboratoriofinal.domain.network.util.NetworkError

interface ApiInterface {
    suspend fun getAllAssets(): Result<AssetListDto, NetworkError>
    suspend fun getAssetProfile(id: String): Result<AssetProfileDto, NetworkError>
}
