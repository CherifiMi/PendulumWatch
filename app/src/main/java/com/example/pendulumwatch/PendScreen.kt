package com.example.pendulumwatch

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.pendulumwatch.components.Pendulum
import com.example.pendulumwatch.components.Statistics


@Composable
fun PendScreen(viewModel: PendViewModel) {

    var frame by remember {
        mutableStateOf(0)
    }
    var v by remember {
        mutableStateOf(false)
    }


    LaunchedEffect(frame) {
        v = !viewModel.uiState.value.isMoving
        frame++
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        AnimatedVisibility(
            visible = v,
            enter = fadeIn(
                initialAlpha = 0.1f,
                animationSpec = tween(durationMillis = 500)
            ),
            exit = fadeOut(
                animationSpec = tween(durationMillis = 200)
            )
        ) {
            Statistics(modifier = Modifier.fillMaxSize(), viewModel = viewModel)
        }
        Pendulum(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(3f / 4f),
            viewModel = viewModel
        )
    }
}



