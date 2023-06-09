package com.robo.randomjokes.model

data class Joke(
    var id: Int,
    var type: String,
    var setup: String,
    var punchline: String
)