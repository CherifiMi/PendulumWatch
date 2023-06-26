package com.example.pendulumwatch

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.pendulumwatch.components.Graph
import com.example.pendulumwatch.components.Pendulum
import com.example.pendulumwatch.components.Statistics
import com.example.pendulumwatch.ui.theme.Blu
import com.example.pendulumwatch.ui.theme.Red


@Composable
fun PendScreen(viewModel: PendViewModel) {
    val state = viewModel.uiState.collectAsState().value


    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        Statistics(modifier = Modifier.fillMaxSize(),viewModel = viewModel)
        Pendulum(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(3f / 4f),
            viewModel = viewModel
        )
    }
}



