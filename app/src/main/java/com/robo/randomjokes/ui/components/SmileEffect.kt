package com.robo.randomjokes.ui.components

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.*
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.random.Random

private const val TARGET_SMILE_SCALE_MULTIPLIER = 1.1f
private const val START_OF_ANIMATION = 0
private const val MIN_SMILE_SIZE = 2f
private const val MAX_SMILE_SIZE = 3.6f
private const val MIN_ANIMATION_DURATION = 1200f
private const val MAX_ANIMATION_DURATION = 1500f
private const val SMILE_TEXT_SIZE = 20
private const val MIN_HEIGHT = 300
private const val MAX_HEIGHT = 800

data class SmileModel(
    val verticalFraction: Float,
    val horizontalFraction: Float,
    val initialScale: Float,
    val duration: Int,
    val emoji: String
)

@Composable
fun SmileEffects(
    modifier: Modifier,
    quantity: Int,
    emoji: String,
    visible: Boolean
) {
    AnimatedVisibility(
        visible = visible,
        enter = slideInVertically(
            initialOffsetY = { it },
            animationSpec = tween(
                durationMillis = MAX_ANIMATION_DURATION.toInt() - 300,
                easing = LinearEasing,
                delayMillis = 100
            )
        ),
        exit = ExitTransition.None
    ) {
        val smileys = remember { calculateParticleParams(quantity, emoji) }
        val transitionState = remember {
            MutableTransitionState(MIN_HEIGHT).apply {
                targetState = MAX_HEIGHT
            }
        }
        val transition = updateTransition(transitionState, label = "height transition")
        val height by transition.animateInt(
            transitionSpec = {
                tween(
                    durationMillis = MAX_ANIMATION_DURATION.toInt(),
                    easing = LinearOutSlowInEasing
                )
            },
            label = "height animation of particles"
        ) { it }

        Layout(
            modifier = modifier.padding(bottom = 10.dp),
            content = {
                for (i in 0 until quantity) {
                    SmileEffect(smileys[i])
                }
            }
        ) { measurables, constraints ->
            val placeables = measurables.map { it.measure(constraints) }
            layout(constraints.maxWidth, height) {
                placeables.forEachIndexed { index, placeable ->
                    val params = smileys[index]
                    placeable.placeRelative(
                        x = (params.horizontalFraction * constraints.maxWidth).toInt() - constraints.maxWidth / 2,
                        y = (params.verticalFraction * height).toInt() - height / 2
                    )
                }
            }
        }
    }
}

private fun calculateParticleParams(quantity: Int, emoji: String): List<SmileModel> {
    val random = Random(System.currentTimeMillis().toInt())
    val result = mutableListOf<SmileModel>()
    for (i in 0 until quantity) {
        val verticalFraction = random.nextDouble(from = 0.0, until = 1.0).toFloat()
        val horizontalFraction = random.nextDouble(from = 0.0, until = 1.0).toFloat()

        val model =
            SmileModel(
                verticalFraction = verticalFraction,
                horizontalFraction = horizontalFraction,
                initialScale = lerp(MIN_SMILE_SIZE, MAX_SMILE_SIZE, verticalFraction),
                duration = lerp(
                    MIN_ANIMATION_DURATION,
                    MAX_ANIMATION_DURATION,
                    verticalFraction
                ).toInt(),
                emoji = emoji
            )
        result.add(
            model
        )
    }

    return result

}

private fun lerp(start: Float, stop: Float, fraction: Float) =
    (start * (1 - fraction) + stop * fraction)

@Composable
private fun SmileEffect(model: SmileModel) {
    val transitionState = remember {
        MutableTransitionState(0.1f).apply {
            targetState = 0f
        }
    }

    val targetScale = remember { model.initialScale * TARGET_SMILE_SCALE_MULTIPLIER }

    val transition = updateTransition(transitionState, label = "particle transition")

    val alpha by transition.animateFloat(
        transitionSpec = {
            keyframes {
                durationMillis = model.duration
                0.1f at START_OF_ANIMATION
                1f at (model.duration * 0.1f).toInt()
                1f at (model.duration * 0.8f).toInt()
                0f at model.duration
            }
        },
        label = "alpha animation of particle"
    ) { it }

    val scale by transition.animateFloat(
        transitionSpec = {
            keyframes {
                durationMillis = model.duration
                model.initialScale at START_OF_ANIMATION
                model.initialScale at (model.duration * 0.7f).toInt()
                targetScale at model.duration
            }
        },
        label = "scale animation of particle"
    ) { it }

    Text(
        modifier = Modifier
            .wrapContentSize()
            .scale(scale)
            .alpha(alpha),
        text = model.emoji,
        fontSize = SMILE_TEXT_SIZE.sp
    )
}

@Preview
@Composable
fun SmileEffectPreview() {
    //SmileEffect(modifier = Modifier.fillMaxWidth())
}