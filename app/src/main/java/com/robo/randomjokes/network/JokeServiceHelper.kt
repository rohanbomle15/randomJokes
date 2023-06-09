package com.robo.randomjokes.network

class JokeServiceHelper(private val randomJokeService: RandomJokeService) {
    suspend fun getJoke() = randomJokeService.getRandomJoke()
}