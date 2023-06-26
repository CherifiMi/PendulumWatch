package com.example.pendulumwatch

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier


@Composable
fun PendScreen(viewModel: PendViewModel) {
    val state = viewModel.uiState.collectAsState().value

    Box(modifier = Modifier
        .fillMaxSize()
        .clickable { viewModel.change() }
    ){
        Text(text = state.isMoving.toString())
    }
}