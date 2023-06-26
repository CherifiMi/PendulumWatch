package com.example.pendulumwatch

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import com.example.pendulumwatch.ui.theme.Red


@Composable
fun PendScreen(viewModel: PendViewModel) {
    val state = viewModel.uiState.collectAsState().value

    /*var scale by remember { mutableStateOf(1f) }
    var rotation by remember { mutableStateOf(0f) }
    var offset by remember { mutableStateOf(Offset.Zero) }
    val states = rememberTransformableState { zoomChange, offsetChange, rotationChange ->
        scale *= zoomChange
        rotation += rotationChange
        offset += offsetChange
    }*/

    val scale by animateFloatAsState(
        targetValue =
        if (state.isMoving)  1f else .8f
    )
    val translation by animateOffsetAsState(
        targetValue =
        if (state.isMoving) Offset(0f, 0f) else Offset(-50f, -50f)
    )


    Box(modifier = Modifier
        .fillMaxSize()
        .graphicsLayer(
            scaleX = scale,
            scaleY = scale,
            translationX = translation.x,
            translationY = translation.y
        )
        /*.transformable(state = states)*/
        .clickable { viewModel.change() }
        .background(Red)
    ) {
        Text(text = state.isMoving.toString())
    }
}