package com.example.pendulumwatch

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.example.pendulumwatch.components.Pendulum
import com.example.pendulumwatch.ui.theme.Red


@Composable
fun PendScreen(viewModel: PendViewModel) {
    val state = viewModel.uiState.collectAsState().value

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        AnimatedVisibility(
            visible = state.isMoving,
            enter = fadeIn(
                initialAlpha = 0.1f,
                animationSpec = tween(durationMillis = 500)
            ),
            exit = fadeOut(
                animationSpec = tween(durationMillis = 200)
            )
        ) {
            Statistics(modifite = Modifier,viewModel = viewModel)
        }

        Pendulum(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(3f / 4f),
            viewModel = viewModel
        )
    }



}



@Composable
fun Statistics(modifite: Modifier.Companion, viewModel: PendViewModel) {
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
