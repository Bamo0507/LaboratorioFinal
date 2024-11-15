package com.bryan.laboratoriofinal.presentation.dataProfile

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.*
import androidx.savedstate.SavedStateRegistryOwner
import com.bryan.laboratoriofinal.data.local.DatabaseModule
import com.bryan.laboratoriofinal.data.network.KtorApi
import com.bryan.laboratoriofinal.data.repositories.AssetRepository
import com.bryan.laboratoriofinal.di.KtorDependencies
import com.bryan.laboratoriofinal.domain.network.util.onError
import com.bryan.laboratoriofinal.domain.network.util.onSuccess
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class AssetProfileViewModel(
    private val repository: AssetRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val assetId = savedStateHandle.get<String>("id") ?: ""

    private val _state = MutableStateFlow(AssetProfileState())
    val state = _state.asStateFlow()

    init {
        getAssetByID()
    }

    fun getAssetByID() {
        viewModelScope.launch {
            repository.getAssetById(assetId)
                .onStart {
                    _state.update { it.copy(isLoading = true) }
                }
                .collect { result ->
                    result.onSuccess { asset ->
                        _state.update {
                            it.copy(
                                isLoading = false,
                                data = asset,
                                isError = false
                            )
                        }
                    }.onError { error ->
                        if (_state.value.data.id.isEmpty()) {
                            _state.update {
                                it.copy(
                                    isLoading = false,
                                    isError = true
                                )
                            }
                        } else {
                            _state.update {
                                it.copy(
                                    isLoading = false,
                                    isError = false
                                )
                            }
                        }
                    }
                }
        }
    }

    companion object {
        fun provideFactory(
            context: Context,
            owner: SavedStateRegistryOwner,
            defaultArgs: Bundle? = null
        ): ViewModelProvider.Factory {
            return object : AbstractSavedStateViewModelFactory(owner, defaultArgs) {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(
                    key: String, modelClass: Class<T>, handle: SavedStateHandle
                ): T {
                    val api = KtorApi(KtorDependencies.provideHttpClient())
                    val database = DatabaseModule.provideDatabase(context)
                    val repository = AssetRepository(api, database.assetDao())
                    return AssetProfileViewModel(repository, handle) as T
                }
            }
        }
    }
}


