package com.robo.randomjokes.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object JokeRetrofitBuilder {

    private const val BASE_URL = "https://official-joke-api.appspot.com/"

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
    }

    val apiService: RandomJokeService = getRetrofit().create(RandomJokeService::class.java)
}