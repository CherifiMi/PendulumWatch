package com.example.pendulumwatch.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.example.pendulumwatch.ui.theme.Red


@Composable
fun Graph(data: MutableList<Float>, modifier: Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ){
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(4 / 2f)
        ) {
            drawGraph(data)
        }
    }
}


fun DrawScope.drawGraph(data: MutableList<Float>) {
    val path = generatePath(data, size)
    val fillPath = Path()
    drawPath(path, Red, style = Stroke(6.dp.toPx()))

    fillPath.addPath(path)
    fillPath.lineTo(size.width, size.height)

}

fun generatePath(data: MutableList<Float>, size: Size): Path {
    val graphData = data.subList(if (data.size>50)data.size-50 else 0,data.size-1)
    val path = Path()
    val highest = data.max() - data.min()
    graphData.add(0f)
    path.moveTo(
        0f,
        size.height / 2 - (size.height / 2 * ((graphData[0] * 100f / highest) / 100))
    )

    graphData.forEachIndexed { i, d ->
        val x = (i + 1f) * (size.width / graphData.size)
        val y = size.height / 2 - (size.height / 2 * ((d * 100f / highest) / 100))

        path.lineTo(x, y)
    }
    return path
}
