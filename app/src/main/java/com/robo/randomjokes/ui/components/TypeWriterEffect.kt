package com.robo.randomjokes.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.robo.randomjokes.ui.theme.Purple700
import kotlinx.coroutines.delay

@Composable
fun TypeWriterEffect(textInput: String) {

    var textToDisplay by remember {
        mutableStateOf("")
    }
    var textCharsList: List<Char> = textInput.toList()

    var showSmileys by remember { mutableStateOf(false) }

    val smileysString: List<String> = listOf("\uD83E\uDD2A","\uD83E\uDD23","\uD83D\uDE02", "\uD83E\uDD23", "\uD83D\uDE1C", "\uD83D\uDE06", "\uD83D\uDE1D")

    Box(modifier = Modifier.fillMaxWidth().height(250.dp)) {

        LaunchedEffect(
            key1 = textInput,
        ) {
            delay(3000)
            textCharsList.forEachIndexed { charIndex, _ ->
                textToDisplay = textInput.take(
                    n = charIndex + 1,
                ) + ""
                delay(160)
            }
            textCharsList = emptyList()
            showSmileys = ! showSmileys
        }

        Text(
            modifier = Modifier
                .padding(20.dp)
                .animateContentSize(
                    animationSpec = tween(durationMillis = 200)
                ),
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(fontWeight = FontWeight.W900, color = Purple700)
                ) {
                    append(textToDisplay)
                }
            },
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
        )

        SmileEffects(modifier = Modifier.fillMaxWidth().fillMaxHeight(), quantity = 20, emoji = smileysString.random(), visible = showSmileys)
    }
}

@Preview
@Composable
fun TypeWriterEffectPreview() {
    TypeWriterEffect(textInput = "This is punch line")
}