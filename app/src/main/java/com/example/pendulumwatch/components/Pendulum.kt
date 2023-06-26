package com.example.pendulumwatch.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.example.pendulumwatch.*
import com.example.pendulumwatch.ui.theme.Blu
import com.example.pendulumwatch.ui.theme.Grn
import com.example.pendulumwatch.ui.theme.Red
import kotlin.math.*

@Composable
fun Pendulum(modifier: Modifier = Modifier, viewModel: PendViewModel) {
    val state = viewModel.uiState.collectAsState().value


    val scale by animateFloatAsState(
        targetValue =
        if (state.isMoving) 1f else 0.8f
    )
    val translation by animateOffsetAsState(
        targetValue =
        if (state.isMoving) Offset(-200f, 0f) else Offset(0f, -100f)
    )

    var frame by remember {
        mutableStateOf(0)
    }
    LaunchedEffect(frame) {

        viewModel.updateP()

        frame++
    }

    Canvas(
        modifier
            .fillMaxSize()
            .graphicsLayer(
                scaleX = scale,
                scaleY = scale,
                translationX = translation.x,
                translationY = translation.y
            )
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = {
                        state.isStuck = true && nearBall(it, state.location)
                    },
                    onDragEnd = { state.isStuck = false },
                    onDrag = { change, dragAmount ->
                        change.consumeAllChanges()
                        state.pressPos = change.position
                    })
            }
    ) {
        with(state) {
            origen = Offset(size.width / 2f, 0f)

            if (isMoving) {
                drawArc(
                    color = Blu,
                    startAngle = 50f,
                    sweepAngle = 40f,
                    useCenter = false,
                    size = Size(r * 2, r * 2),
                    topLeft = Offset(origen.x - r, -r),
                    style = Stroke(
                        width = 4.dp.toPx(),
                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(15f, 15f), 0f),
                        cap = StrokeCap.Round
                    )
                )

                drawArc(
                    color = Color.White,
                    alpha = if (spinning > 90 || spinning < 50) 0f else 0.7f,
                    startAngle = spinning,
                    sweepAngle = 3f,
                    useCenter = false,
                    size = Size(r * 2, r * 2),
                    topLeft = Offset(origen.x - r, -r),
                    style = Stroke(
                        width = 4.dp.toPx(),
                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(15f, 15f), 0f),
                        cap = StrokeCap.Round
                    )
                )
            }

            drawLine(
                color = Grn,
                start = origen,
                end = location,
                strokeWidth = 2.dp.toPx()
            )
            drawCircle(if (isStuck) Red else Grn, radius = 5 * 8f, center = location)
        }
    }
}

fun nearBall(pressPos: Offset, location: Offset): Boolean {
    val x = abs(pressPos.x - location.x)
    val y = abs(pressPos.y - location.y)

    return x < 80 && y < 80
}