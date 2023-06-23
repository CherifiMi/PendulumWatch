package com.example.pendulumwatch

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pendulumwatch.ui.theme.Blu
import com.example.pendulumwatch.ui.theme.Grn
import com.example.pendulumwatch.ui.theme.PendulumWatchTheme
import com.example.pendulumwatch.ui.theme.Red
import kotlinx.coroutines.delay
import kotlin.collections.ArrayList
import kotlin.math.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PendulumWatchTheme {
                App()
            }
        }
    }
}

val Show = mutableStateOf(false)
val GAng = mutableListOf<Float>()
val GVel = mutableListOf<Float>()
val GAcc = mutableListOf<Float>()
val level = mutableStateOf(0)


@Composable
fun App() {
    Box(
        Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.TopCenter
    ) {
        AnimatedVisibility(
            visible = Show.value,
            enter = fadeIn(
                initialAlpha = 0.1f,
                animationSpec = tween(durationMillis = 500)
            ),
            exit = fadeOut(
                animationSpec = tween(durationMillis = 200)
            )
        ) {
            Column(
                Modifier
                    .fillMaxSize()
                    .background(Color.Black)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f),
                    contentAlignment = Alignment.Center
                )
                {
                    Graph(data = GAng)
                }
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
                        data = GAcc,
                        txt = "α"
                    )
                    Track(
                        modifier = Modifier
                            .padding(16.dp)
                            .height(100.dp),
                        data = GVel,
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
                        Ecc(modifier = Modifier.offset(0.dp, (-190).dp).alpha(if (level.value>=0) 1f else 0f),lev = if (level.value>0) level.value else 0)
                        Ecc(modifier = Modifier.rotate(180f ).alpha(if (level.value<0) 1f else 0f), lev = if (level.value<0) level.value else 0)
                    }
                }

            }
        }

        Pend(
            Modifier
                .fillMaxWidth()
                .aspectRatio(3f / 4f)
                .padding(horizontal = 48.dp)
        )
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
fun Graph(data: MutableList<Float>) {

    var graphData by remember {
        mutableStateOf(arrayListOf(700f))
    }

    var frame by remember {
        mutableStateOf(0)
    }

    LaunchedEffect(frame) {

        graphData.add(data.last() * 10f)

        if (graphData.size > 500) {
            graphData.removeAt(0)
        }

        frame++
    }

    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(4 / 2f)
    )
    {
        drawGraph(graphData)
    }
}


fun DrawScope.drawGraph(graphData: ArrayList<Float>) {
    val path = generatePath(graphData, size)
    val fillPath = Path()
    drawPath(path, Red, style = Stroke(6.dp.toPx()))

    fillPath.addPath(path)
    fillPath.lineTo(size.width, size.height)

}

fun generatePath(graphData: ArrayList<Float>, size: Size): Path {
    val path = Path()
    val highest = 6000//graphData.max() - graphData.min()

    path.moveTo(0f, size.height / 2 - (size.height / 2 * ((graphData[0] * 100f / highest) / 100)))

    graphData.forEachIndexed { i, d ->
        val x = (i + 1f) * (size.width / graphData.size)
        val y = size.height / 2 - (size.height / 2 * ((d * 100f / highest) / 100))

        path.lineTo(x, y)
    }
    return path
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



        (5 / GAng.max() * (angle * 1000f)).also{
            if (!it.isNaN()){
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

fun nearBall(mouse: Offset, location: Offset): Boolean {
    var x = abs(mouse.x - location.x)
    var y = abs(mouse.y - location.y)

    return x < 80 && y < 80
}