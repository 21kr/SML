package com.mrp.sml.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mrp.sml.core.models.ConnectionState
import com.mrp.sml.domain.repository.ConnectionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val connectionRepository: ConnectionRepository
) : ViewModel() {

    private val _connectionState = MutableStateFlow(ConnectionState.IDLE)
    val connectionState: StateFlow<ConnectionState> = _connectionState.asStateFlow()

    private val _isDiscovering = MutableStateFlow(false)
    val isDiscovering: StateFlow<Boolean> = _isDiscovering.asStateFlow()

    init {
        viewModelScope.launch {
            connectionRepository.observeConnectionState().collect { state ->
                _connectionState.value = state
            }
        }
    }

    fun startDiscovery() {
        viewModelScope.launch {
            _isDiscovering.value = true
            connectionRepository.startDiscovery()
        }
    }

    fun stopDiscovery() {
        viewModelScope.launch {
            connectionRepository.stopDiscovery()
            _isDiscovering.value = false
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.launch {
            connectionRepository.stopDiscovery()
        }
    }
}
