package com.bryan.laboratoriofinal.di

import com.bryan.laboratoriofinal.data.network.HttpClientFactory
import io.ktor.client.HttpClient

object KtorDependencies {
    // Variable privada para almacenar el cliente HTTP, inicialmente es null.
    // Solo se creará una instancia de HttpClient cuando se necesite.
    private var httpClient: HttpClient? = null

    // Función privada que construye un nuevo cliente HTTP llamando a una fábrica
    // (asumimos que HttpClientFactory.create() es una función que lo crea).
    private fun buildHttpClient(): HttpClient = HttpClientFactory.create()

    // Función pública que provee una instancia de HttpClient.
    fun provideHttpClient(): HttpClient {
        // Verifica si ya existe una instancia en httpClient.
        return httpClient ?: synchronized(this) { // synchronized evita que varios hilos creen instancias al mismo tiempo.
            // Si httpClient sigue siendo null, entonces crea una nueva instancia.
            // La instancia creada se guarda en httpClient para que la próxima llamada
            // no tenga que crear una nueva.
            httpClient ?: buildHttpClient().also { httpClient = it }
        }
    }
}
