package com.bryan.laboratoriofinal.data.repositories

import com.bryan.laboratoriofinal.data.local.dao.AssetDao
import com.bryan.laboratoriofinal.data.local.entity.toDomainModel
import com.bryan.laboratoriofinal.data.local.entity.toEntity
import com.bryan.laboratoriofinal.data.network.dto.mapToAssetModel
import com.bryan.laboratoriofinal.domain.model.Asset
import com.bryan.laboratoriofinal.domain.network.ApiInterface
import com.bryan.laboratoriofinal.domain.network.util.NetworkError
import com.bryan.laboratoriofinal.domain.network.util.Result
import com.bryan.laboratoriofinal.domain.network.util.onError
import com.bryan.laboratoriofinal.domain.network.util.onSuccess
import com.bryan.laboratoriofinal.domain.network.util.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AssetRepository(
    private val api: ApiInterface,
    private val assetDao: AssetDao
) {

    suspend fun getAllAssets(): Flow<Result<List<Asset>, NetworkError>> = flow {
        val localData = assetDao.getAllAssets().map { it.toDomainModel() }
        if (localData.isNotEmpty()) {
            emit(Result.Success(localData))
        }

        val networkResult = api.getAllAssets()
        networkResult.onSuccess { response ->
            val assets = response.data.map { it.mapToAssetModel(timestamp = response.timestamp) }
            assetDao.insertAssets(assets.map { it.toEntity() })
            emit(Result.Success(assets))
        }.onError { error ->
            if (localData.isEmpty()) {
                emit(Result.Error(error))
            }
        }
    }

    suspend fun getAssetById(id: String): Flow<Result<Asset, NetworkError>> = flow {
        val localAsset = assetDao.getAssetById(id)
        if (localAsset != null) {
            emit(Result.Success(localAsset.toDomainModel()))
        }

        val networkResult = api.getAssetProfile(id)
        networkResult.onSuccess { response ->
            val asset = response.data.mapToAssetModel(timestamp = response.timestamp)
            assetDao.insertAsset(asset.toEntity())
            emit(Result.Success(asset))
        }.onError { error ->
            if (localAsset == null) {
                emit(Result.Error(error))
            }
        }
    }

    suspend fun saveAssetsOffline(): Result<Unit, NetworkError> {
        val networkResult = api.getAllAssets()
        return networkResult.map { response ->
            val currentTime = System.currentTimeMillis()
            val assets = response.data.map {
                it.mapToAssetModel(timestamp = currentTime)
            }
            assetDao.insertAssets(assets.map { it.toEntity() })
            Unit
        }
    }
}

