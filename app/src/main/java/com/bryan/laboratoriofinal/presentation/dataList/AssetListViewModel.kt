package com.bryan.laboratoriofinal.presentation.dataList

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.bryan.laboratoriofinal.data.local.DatabaseModule
import com.bryan.laboratoriofinal.data.network.KtorApi
import com.bryan.laboratoriofinal.data.repositories.AssetRepository
import com.bryan.laboratoriofinal.di.KtorDependencies
import com.bryan.laboratoriofinal.domain.network.util.onError
import com.bryan.laboratoriofinal.domain.network.util.onSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AssetListViewModel(
    private val repository: AssetRepository,
    context: Context
) : ViewModel() {

    private val _state = MutableStateFlow(AssetListState())
    val state = _state.asStateFlow()

    init {
        getAssets()
    }

    fun getAssets() {
        viewModelScope.launch {
            repository.getAllAssets()
                .onStart {
                    _state.update { it.copy(isLoading = true) }
                }
                .collect { result ->
                    result.onSuccess { assets ->
                        _state.update {
                            it.copy(
                                isLoading = false,
                                data = assets,
                                isError = false
                            )
                        }
                    }.onError { error ->
                        if (_state.value.data.isEmpty()) {
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

    fun saveDataOffline() {
        viewModelScope.launch {
            val result = repository.saveAssetsOffline()
            result.onSuccess {
                _state.update {
                    it.copy(lastSaveTime = System.currentTimeMillis())
                }
            }
        }
    }

    companion object {
        fun provideFactory(context: android.content.Context): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    val api = KtorApi(KtorDependencies.provideHttpClient())
                    val database = DatabaseModule.provideDatabase(context)
                    val repository = AssetRepository(api, database.assetDao())
                    return AssetListViewModel(repository, context) as T
                }
            }
    }
}
