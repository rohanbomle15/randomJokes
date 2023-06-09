package com.robo.randomjokes.repository

import com.robo.randomjokes.network.JokeServiceHelper

class JokesRepository(private val jokeServiceHelper: JokeServiceHelper) {
    suspend fun getNextJoke() = jokeServiceHelper.getJoke()
}