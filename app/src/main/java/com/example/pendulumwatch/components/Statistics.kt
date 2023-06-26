package com.example.pendulumwatch.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pendulumwatch.*
import com.example.pendulumwatch.ui.theme.Blu
import com.example.pendulumwatch.ui.theme.Grn
import com.example.pendulumwatch.ui.theme.Red
import kotlinx.coroutines.delay
import kotlin.math.absoluteValue


@Composable
fun Statistics(modifier: Modifier, viewModel: PendViewModel) {
    val state = viewModel.uiState.collectAsState().value

    Column(modifier = modifier) {

        Graph(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f),
            data = state.gAng
        )
        Timer(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(2 / 0.8f)
        )

        Row(
            Modifier
                .fillMaxWidth()
                .height(200.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Track(
                modifier = Modifier
                    .padding(16.dp)
                    .height(100.dp),
                data = state.gAcc,
                txt = "α"
            )
            Track(
                modifier = Modifier
                    .padding(16.dp)
                    .height(100.dp),
                data = state.gVel,
                txt = "ν"
            )
            Column(
                Modifier
                    .padding(8.dp)
                    .fillMaxHeight()
                    .offset(0.dp, 200.dp)
                    .width(100.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                /*Ecc(
                    modifier = Modifier
                        .offset(0.dp, (-190).dp)
                        .alpha(if (level.value >= 0) 1f else 0f),
                    lev = if (level.value > 0) level.value else 0
                )
                Ecc(
                    modifier = Modifier
                        .rotate(180f)
                        .alpha(if (level.value < 0) 1f else 0f),
                    lev = if (level.value < 0) level.value else 0
                )*/
            }
        }

    }
}


@Composable
fun Ecc(modifier: Modifier = Modifier, lev: Int) {
    var s by remember { mutableStateOf(0) }
    var l by remember { mutableStateOf(0) }
    LaunchedEffect(s) {
        l = level.value.absoluteValue
        s++
    }
    Canvas(modifier) {
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


@Composable
fun Timer(modifier: Modifier) {
    var timer by remember { mutableStateOf(60) }
    LaunchedEffect(key1 = timer) {
        if (timer > 0) {
            delay(1_000)
            timer -= 1
        }
    }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {

        Text(
            text = "00",
            fontSize = 80.sp,
            fontWeight = FontWeight(300),
            color = Grn
        )
        Text(
            text = "m  ",
            fontSize = 40.sp,
            fontWeight = FontWeight(300),
            color = Blu
        )
        Text(
            text = timer.toString(),
            fontSize = 80.sp,
            fontWeight = FontWeight(300),
            color = Grn
        )
        Text(
            text = "s",
            fontSize = 40.sp,
            fontWeight = FontWeight(300),
            color = Blu
        )
    }
}


@Composable
fun Track(modifier: Modifier = Modifier, data: MutableList<Float>, txt: String) {

    var frame by remember {
        mutableStateOf(0)
    }

    var max by remember {
        mutableStateOf(0f)
    }
    var value by remember {
        mutableStateOf(0f)
    }


    LaunchedEffect(frame) {
        max = data.max() - data.min()
        value = data.last().absoluteValue
        frame++
    }


    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .aspectRatio(1f)
            .drawBehind {
                drawArc(
                    color = Grn,
                    alpha = 0.4f,
                    startAngle = 120f,
                    sweepAngle = 300f,
                    useCenter = false,
                    style = Stroke(width = 20.dp.toPx(), cap = StrokeCap.Round)
                )
                drawArc(
                    color = Grn,
                    startAngle = 120f,
                    sweepAngle = 300f * (value / max),
                    useCenter = false,
                    style = Stroke(width = 20.dp.toPx(), cap = StrokeCap.Round)
                )
            }

    ) {
        Text(
            text = txt,
            fontSize = 32.sp,
            fontWeight = FontWeight(700),
            color = Blu
        )
    }


}