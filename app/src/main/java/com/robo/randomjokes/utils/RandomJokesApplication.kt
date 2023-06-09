package com.robo.randomjokes.utils

import android.app.Application
import android.content.Context

class RandomJokesApplication: Application() {

    companion object RandomJokesApp {
        var mContext: Context? = null
    }

    override fun onCreate() {
        super.onCreate()
        mContext = applicationContext
    }
}