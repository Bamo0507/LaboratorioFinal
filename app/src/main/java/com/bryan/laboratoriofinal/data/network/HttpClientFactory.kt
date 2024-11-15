package com.bryan.laboratoriofinal.data.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.ANDROID
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object HttpClientFactory {
    fun create(): HttpClient {
        // Especificamos el Engine a utilziar para procesar los requests.
        //Esto se puede copiar tal cual, no debería haber problema
        return HttpClient(CIO.create()) {
            // Plugin para loggear el trafico de red
            install(Logging) {
                level = LogLevel.ALL // Define que queremos ver TODA la info
                logger = Logger.ANDROID
            }

            // Plugin para serializar el contenido de JSON a Kotlin - esta es más que todo una buena práctica
            install(ContentNegotiation) {
                json(
                    // Usamos la libreria ktor serialization
                    json = Json {
                        // Si el API retorna una propiedad que no esta en la data class, la ignora
                        ignoreUnknownKeys = true //Importante, facilitará el proceso para la info que no coloque en la o las model classes que se utilicen
                    }
                )
            }
            // Por defecto, todas las llamadas tendran el tipo JSON.
            //Se pone siempre, puedo directamente copiarlo
            defaultRequest {
                contentType(ContentType.Application.Json)
            }
        }
    }
}

