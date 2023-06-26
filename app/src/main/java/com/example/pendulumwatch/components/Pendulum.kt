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
        if (state.isMoving) 1f else .8f
    )
    val translation by animateOffsetAsState(
        targetValue =
        if (state.isMoving) Offset(0f, 0f) else Offset(-100f, -50f)
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





@Composable
fun Pend(modifier: Modifier = Modifier) {


    var frame by remember {
        mutableStateOf(0)
    }


    var spining by remember {
        mutableStateOf(90f)
    }
    var scaling by remember {
        mutableStateOf(1f)
    }


    var isStuck by remember {
        mutableStateOf(false)
    }
    var pressPos by remember {
        mutableStateOf(Offset(0f, 0f))
    }
    var origen by remember {
        mutableStateOf(Offset(0f, 0f))
    }
    var angle by remember {
        mutableStateOf(0f)
    }
    var aVelocity by remember {
        mutableStateOf(0f)
    }
    var aAcceleration by remember {
        mutableStateOf(0f)
    }
    var gravity by remember {
        mutableStateOf(0.4f)
    }
    var location by remember {
        mutableStateOf(Offset(0f, 0f))
    }
    var dumping by remember {
        mutableStateOf(0.9997f)
    }
    var r by remember {
        mutableStateOf(800f)
    }
    var isMoving by remember {
        mutableStateOf(false)
    }



    LaunchedEffect(frame) {
        aAcceleration = ((-1 * gravity / r) * sin(angle))
        aVelocity += aAcceleration
        angle += aVelocity

        aVelocity *= dumping

        location = Offset(r * sin(angle), r * cos(angle))
        location = location.plus(origen)

        if (isStuck) {
            aVelocity = 0f
            var c2 = origen.minus(Offset(pressPos.x, pressPos.y))
            angle = (atan2(-1 * c2.y, c2.x) - (90f * PI / 180f)).toFloat()
        }

        spining -= 0.5f
        if (spining < 0) {
            spining = 90f
        }

        if (angle > 0.7f) {
            isStuck = false
        }

        isMoving = (isStuck && angle != 0f) || (!isStuck && angle == 0f)

        if (!isMoving) {
            scaling = 0.83f
            Show.value = true
        } else {
            scaling = 1f
        }



        GAng.add(angle * 1000f)
        GVel.add(aVelocity * 1000f)
        GAcc.add(aAcceleration * 1000f)



        (5 / GAng.max() * (angle * 1000f)).also {
            if (!it.isNaN()) {
                level.value = it.roundToInt()
            }
        }

        frame++
    }


    Canvas(
        modifier
            .scale(scaling)
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = {
                        isStuck = true && nearBall(it, location)
                    },
                    onDragEnd = { isStuck = false },
                    onDrag = { change, dragAmount ->
                        change.consumeAllChanges()
                        pressPos = change.position
                    })
            }
    ) {
        origen = Offset(40f, 0f)
        translate(if (isMoving) 0f else 220f, if (isMoving) 0f else -100f) {


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
                    alpha = if (spining > 90 || spining < 50) 0f else 0.7f,
                    startAngle = spining,
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
                Grn,
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