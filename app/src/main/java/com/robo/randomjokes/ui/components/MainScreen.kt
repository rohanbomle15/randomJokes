package com.robo.randomjokes.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.robo.randomjokes.viewmodel.MainViewModel
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

@Composable
fun MainScreen(viewModel: MainViewModel) {

    val joke = viewModel.jokeLiveData.observeAsState()

    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text(text = "Next Joke", color = Color.White, fontSize = 20.sp) },
                onClick = {
                    MainScope().launch {
                        viewModel.getNextJoke()
                    }
                }
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(padding),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            )   {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)) {
                    AdMobBanner()
                }
                joke.value?.let {
                    JokeCard(joke = it)
                }
            }
        }
    ) 
}