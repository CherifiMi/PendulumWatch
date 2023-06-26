package com.example.pendulumwatch

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.pendulumwatch.components.Pendulum


@Composable
fun PendScreen(viewModel: PendViewModel) {
    val state = viewModel.uiState.collectAsState().value

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        AnimatedVisibility(
            visible = state.isMoving,
            enter = fadeIn(
                initialAlpha = 0.1f,
                animationSpec = tween(durationMillis = 500)
            ),
            exit = fadeOut(
                animationSpec = tween(durationMillis = 200)
            )
        ) {
            //Statistics(modifite = Modifier,viewModel = viewModel)
        }
        Pendulum(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(3f / 4f),
            viewModel = viewModel
        )
    }
}



