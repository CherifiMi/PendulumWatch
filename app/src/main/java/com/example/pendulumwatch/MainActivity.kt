package com.example.pendulumwatch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pendulumwatch.components.Ecc
import com.example.pendulumwatch.components.Graph
import com.example.pendulumwatch.components.Pend
import com.example.pendulumwatch.components.Track
import com.example.pendulumwatch.ui.theme.Blu
import com.example.pendulumwatch.ui.theme.Grn
import com.example.pendulumwatch.ui.theme.PendulumWatchTheme
import com.example.pendulumwatch.ui.theme.Red
import kotlinx.coroutines.delay
import kotlin.math.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel: PendViewModel by viewModels()

        setContent {
            Surface(color = Color.Black) {
                PendScreen(viewModel)
            }
        }
    }
}

val Show = mutableStateOf(false)
val GAng = mutableListOf<Float>()
val GVel = mutableListOf<Float>()
val GAcc = mutableListOf<Float>()
val level = mutableStateOf(0)

/*
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
                        Ecc(modifier = Modifier
                            .offset(0.dp, (-190).dp)
                            .alpha(if (level.value >= 0) 1f else 0f),lev = if (level.value>0) level.value else 0)
                        Ecc(modifier = Modifier
                            .rotate(180f)
                            .alpha(if (level.value < 0) 1f else 0f), lev = if (level.value<0) level.value else 0)
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

}*/


