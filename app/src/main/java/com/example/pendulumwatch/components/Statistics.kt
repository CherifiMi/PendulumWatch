package com.example.pendulumwatch.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pendulumwatch.PendViewModel
import kotlin.math.roundToInt


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
                AngleMeter(l =  (5/ if(state.gAng.size>0) state.gAng.max() else 0f * state.angle).roundToInt())
            }
        }

    }
}