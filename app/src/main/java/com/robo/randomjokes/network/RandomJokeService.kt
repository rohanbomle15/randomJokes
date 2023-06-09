package com.robo.randomjokes.network

import com.robo.randomjokes.model.Joke
import retrofit2.http.GET

interface RandomJokeService {

    @GET("random_joke")
    suspend fun getRandomJoke(): Joke
}