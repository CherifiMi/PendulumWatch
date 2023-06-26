package com.example.pendulumwatch.components

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.example.pendulumwatch.ui.theme.Red
import kotlin.math.absoluteValue
import kotlin.math.roundToInt


@Composable
fun AngleMeter(modifier: Modifier = Modifier, gAng: MutableList<Float>) {
    var frame by remember {
        mutableStateOf(0)
    }

    var l by remember {
        mutableStateOf(0)
    }

    LaunchedEffect(frame) {

        l = (5/  gAng.max() * gAng.last()).let {x->
            if (!x.isNaN()){
                x.roundToInt().absoluteValue
            }else{
                0
            }
        }

        frame++
    }



    Column(modifier.offset(y = -180.dp)) {
        Canvas(
            Modifier
                .alpha(if (gAng.last() > 0) 1f else 0f)
                .weight(1f, true)) {
            drawCircle(
                Red,
                alpha = 1f,
                radius = 30f,
                center = Offset(size.width / 2f, 30f * (5 - l + 1)),
                style = Stroke(width = 7.dp.toPx())
            )
            drawCircle(
                Red,
                alpha = if (l >= 4) 1f / 2 else 0f,
                radius = 30f,
                center = Offset(size.width / 2f, 30f * 2),
                style = Stroke(width = 7.dp.toPx())
            )
            drawCircle(
                Red,
                alpha = if (l >= 3) 1f / 3 else 0f,
                radius = 30f,
                center = Offset(size.width / 2f, 30f * 3),
                style = Stroke(width = 7.dp.toPx())
            )
            drawCircle(
                Red,
                alpha = if (l >= 2) 1f / 4 else 0f,
                radius = 30f,
                center = Offset(size.width / 2f, 30f * 4),
                style = Stroke(width = 7.dp.toPx())
            )
            drawCircle(
                Red,
                alpha = if (l >= 1) 1f / 5 else 0f,
                radius = 30f,
                center = Offset(size.width / 2f, 30f * 5),
                style = Stroke(width = 7.dp.toPx())
            )
            drawCircle(
                Red,
                alpha = if (l >= 0) 0f else 0f,
                radius = 30f,
                center = Offset(size.width / 2f, 30f * 5),
                style = Stroke(width = 7.dp.toPx())
            )
        }
        Canvas(
            Modifier
                .alpha(if (gAng.last() <= 0) 1f else 0f)
                .weight(1f, true)
                .rotate(180f)) {
            drawCircle(
                Red,
                alpha = 1f,
                radius = 30f,
                center = Offset(size.width / 2f, 30f * (5 - l + 1)),
                style = Stroke(width = 7.dp.toPx())
            )
            drawCircle(
                Red,
                alpha = if (l >= 4) 1f / 2 else 0f,
                radius = 30f,
                center = Offset(size.width / 2f, 30f * 2),
                style = Stroke(width = 7.dp.toPx())
            )
            drawCircle(
                Red,
                alpha = if (l >= 3) 1f / 3 else 0f,
                radius = 30f,
                center = Offset(size.width / 2f, 30f * 3),
                style = Stroke(width = 7.dp.toPx())
            )
            drawCircle(
                Red,
                alpha = if (l >= 2) 1f / 4 else 0f,
                radius = 30f,
                center = Offset(size.width / 2f, 30f * 4),
                style = Stroke(width = 7.dp.toPx())
            )
            drawCircle(
                Red,
                alpha = if (l >= 1) 1f / 5 else 0f,
                radius = 30f,
                center = Offset(size.width / 2f, 30f * 5),
                style = Stroke(width = 7.dp.toPx())
            )
            drawCircle(
                Red,
                alpha = if (l >= 0) 0f else 0f,
                radius = 30f,
                center = Offset(size.width / 2f, 30f * 5),
                style = Stroke(width = 7.dp.toPx())
            )
        }
    }
}
