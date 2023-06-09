package com.robo.randomjokes.ui.components


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.robo.randomjokes.model.Joke
import com.robo.randomjokes.ui.theme.*

@Composable
fun JokeCard(joke: Joke) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(25.dp, 50.dp, 25.dp, 50.dp)
            .clickable { },
        elevation = 20.dp,
        backgroundColor = Color.White,
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                modifier = Modifier
                    .background(color = OwlBlue)
                    .height(80.dp)
                    .padding(20.dp)
                    .fillMaxWidth(),
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(fontWeight = FontWeight.W900, color = Color.White)
                    ) {
                        append(joke.type.uppercase())
                    }
                },
                fontSize = 22.sp,
            )
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.padding(20.dp),
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(fontWeight = FontWeight.W800, color = Purple500)
                    ) {
                        append(joke.setup)
                    }
                },
                fontSize = 26.sp,
            )

            if (joke.punchline.isBlank() || joke.punchline.isNullOrEmpty()) {
                TypeWriterEffect(textInput = "")
            } else {
                TypeWriterEffect(textInput = joke.punchline)
            }
        }
    }
}

@Preview
@Composable
fun JokeCardPreview() {
    JokeCard(Joke(1, "general","I just watched a documentary about beavers.","It was the best dam show I ever saw"))
}