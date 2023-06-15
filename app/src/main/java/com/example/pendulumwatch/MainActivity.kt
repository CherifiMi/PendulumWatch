package com.example.pendulumwatch

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.progressSemantics
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pendulumwatch.ui.theme.PendulumWatchTheme
import kotlinx.coroutines.Dispatchers
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



@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun App() {
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
        mutableStateOf(100f)
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
        mutableStateOf(0.997f)
    }
    var r by remember {
        mutableStateOf(0f)
    }

    val update by rememberInfiniteTransition().animateFloat(
        initialValue = 0f,
        targetValue = 600f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 400, easing = FastOutLinearInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    LaunchedEffect(update, Dispatchers.Main){
        aAcceleration = ((-1 * gravity / r) * sin(angle))
        aVelocity += aAcceleration
        angle += aVelocity

        aVelocity *= dumping

        location = Offset(r * sin(angle), r * cos(angle))
        location = location.plus(origen)

        if (isStuck) {
            var c2 = origen.minus(Offset(pressPos.x, pressPos.y))
            angle = (atan2(-1 * c2.y, c2.x) - (90f * PI / 180f)).toFloat()
        }
    }

    Canvas(
        Modifier
            .fillMaxSize()
            .padding(8.dp)
            .aspectRatio(1f)
            .background(Color.Black)
            .pointerInput(Unit) {
                detectDragGestures { change, _ ->
                    pressPos = change.position
                }
                detectTapGestures(
                    onPress = {
                        try {
                            isStuck = true && nearBall(pressPos, location)
                            awaitRelease()
                        } finally {
                            isStuck = false
                        }
                    }
                )
            }
    ) {
        origen = Offset(size.width / 2, 0f)
        r = size.height * 0.7f

        drawRect(Color.White, style = Stroke(width = 2.dp.toPx()))
        drawLine(
            Color.White,
            start = origen,
            end = location,
            strokeWidth = 2.dp.toPx()
        )
        drawCircle(if (isStuck) Color.Red else Color.White, radius = 40f, center = location)
    }
}

fun nearBall(mouse: Offset, location: Offset): Boolean {
    var x = abs(mouse.x - location.x)
    var y = abs(mouse.y - location.y)

    return x < 25 && y < 25
}