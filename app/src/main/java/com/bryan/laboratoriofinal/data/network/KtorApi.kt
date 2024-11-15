package com.bryan.laboratoriofinal.data.network

import com.bryan.laboratoriofinal.data.network.dto.AssetListDto
import com.bryan.laboratoriofinal.data.network.dto.AssetProfileDto
import com.bryan.laboratoriofinal.data.network.util.safeCall
import com.bryan.laboratoriofinal.domain.network.ApiInterface
import com.bryan.laboratoriofinal.domain.network.util.NetworkError
import com.bryan.laboratoriofinal.domain.network.util.Result
import io.ktor.client.HttpClient
import io.ktor.client.request.get

/*
Aclaración importante:

En 'KtorZeldaApi' declaramos la clase que se encargará de devolver safeCalls, básicamente se maneja de manera
segura las llamadas a internet, para lograr jalar los datos que necesitemos.
 */

//Se declara la clase para manejar los endpoints
class KtorApi(
    private val httpClient: HttpClient
) : ApiInterface {
    override suspend fun getAllAssets(): Result<AssetListDto, NetworkError> {
        return safeCall<AssetListDto> {
            httpClient.get(
                "https://api.coincap.io/v2/assets"
            )
        }
    }

    override suspend fun getAssetProfile(id: String): Result<AssetProfileDto, NetworkError> {
        return safeCall<AssetProfileDto> {
            httpClient.get(
                "https://api.coincap.io/v2/assets/$id"
            )
        }
    }
}
