package com.example.pendulumwatch

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


data class PendUiState(
    val isMoving: Boolean = false
)
class PendViewModel: ViewModel() {
    private val _uiSate = MutableStateFlow(PendUiState())
    val uiState: StateFlow<PendUiState> = _uiSate.asStateFlow()

    fun change(){
        _uiSate.update {
            it.copy(
                isMoving = !it.isMoving
            )
        }

        Log.d("VIEWMODELCHANGE", _uiSate.value.toString())
    }
}