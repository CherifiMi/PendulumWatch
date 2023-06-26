package com.example.pendulumwatch.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import com.example.pendulumwatch.PendViewModel
import com.example.pendulumwatch.ui.theme.Red

@Composable
fun Pendulum(modifier: Modifier = Modifier, viewModel: PendViewModel) {
    val state = viewModel.uiState.collectAsState().value


    val scale by animateFloatAsState(
        targetValue =
        if (state.isMoving)  1f else .8f
    )
    val translation by animateOffsetAsState(
        targetValue =
        if (state.isMoving) Offset(0f, 0f) else Offset(-50f, -50f)
    )


    Box(modifier = modifier
        .fillMaxSize()
        .graphicsLayer(
            scaleX = scale,
            scaleY = scale,
            translationX = translation.x,
            translationY = translation.y
        )
        .clickable { viewModel.change() }
        .background(Red)
    ) {
        Text(text = state.isMoving.toString())
    }

}