package com.example.pendulumwatch.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.pendulumwatch.ui.theme.Blu
import com.example.pendulumwatch.ui.theme.Grn
import kotlinx.coroutines.delay


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
