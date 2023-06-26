package com.example.pendulumwatch

import android.util.Log
import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin


data class PendUiState(
    var isMoving: Boolean = false,
    var isStuck: Boolean = false,
    val origen: Offset = Offset(0f, 0f),
    var angle: Float = 0f,
    var aVelocity: Float = 0f,
    var aAcceleration: Float = 0f,
    val gravity: Float = 0.4f,
    var location: Offset = Offset(0f, 0f),
    var pressPos: Offset = Offset(0f, 0f),
    val dumping: Float = 0.9997f,
    val r: Float = 800f,
    val spinning: Float = 0f,
)

class PendViewModel: ViewModel() {
    private val _uiSate = MutableStateFlow(PendUiState())
    val uiState: StateFlow<PendUiState> = _uiSate.asStateFlow()

    fun updateP(){
        _uiSate.update {
            it.apply {
                aAcceleration = ((-1 * gravity / r) * sin(angle))
                aVelocity += aAcceleration
                angle += aVelocity

                aVelocity *= dumping

                location = Offset(r * sin(angle), r * cos(angle))
                location = location.plus(origen)

                angle = it.angle+0.1f

                if (isStuck) {
                    aVelocity = 0f
                    var c2 = origen.minus(Offset(pressPos.x, pressPos.y))
                    angle = (atan2(-1 * c2.y, c2.x) - (90f * PI / 180f)).toFloat()
                }

                spinning -= 0.5f
                if (spinning < 0) {
                    spinning = 90f
                }

                if (angle > 0.7f) {
                    isStuck = false
                }

                isMoving = (isStuck && angle != 0f) || (!isStuck && angle == 0f)

            }
        }
        Log.d("STATEPEND", uiState.value.angle.toString())
    }

    fun change(){
        _uiSate.update {
            it.copy(
                isMoving = !it.isMoving
            )
        }
    }
}