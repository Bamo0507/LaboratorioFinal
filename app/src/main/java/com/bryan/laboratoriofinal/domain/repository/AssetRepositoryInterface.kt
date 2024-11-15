package com.bryan.laboratoriofinal.domain.repository

import com.bryan.laboratoriofinal.domain.model.Asset
import com.bryan.laboratoriofinal.domain.network.util.NetworkError
import com.bryan.laboratoriofinal.domain.network.util.Result
import kotlinx.coroutines.flow.Flow

interface AssetRepositoryInterface {
    suspend fun getAllAssets(): Flow<Result<List<Asset>, NetworkError>>
    suspend fun getAssetById(id: String): Flow<Result<Asset, NetworkError>>
}
