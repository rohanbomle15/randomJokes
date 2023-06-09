package com.robo.randomjokes.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.robo.randomjokes.model.Joke
import com.robo.randomjokes.network.JokeRetrofitBuilder
import com.robo.randomjokes.network.JokeServiceHelper
import com.robo.randomjokes.repository.JokesRepository
import com.robo.randomjokes.utils.RandomJokesApplication
import com.robo.randomjokes.utils.Utils
import kotlinx.coroutines.launch

class MainViewModel(app: Application): AndroidViewModel(app) {

    private var jokesRepository: JokesRepository? = null
    private val _joke = MutableLiveData<Joke>()
    val jokeLiveData: LiveData<Joke> get() = _joke

    init {
            viewModelScope.launch {
                jokesRepository = JokesRepository(JokeServiceHelper(JokeRetrofitBuilder.apiService))
                getNextJoke()
            }
    }

    suspend fun getNextJoke() {
        RandomJokesApplication.mContext?.let { applicationContext ->
            _joke.value = Joke(0, "", "", "")
            if(Utils.isConnectedToInternet(context = applicationContext)) {
                _joke.value = jokesRepository?.getNextJoke()
            } else {
                _joke.value = getNoInternetJoke()
            }
        }
    }

    private fun getNoInternetJoke(): Joke {
        return Joke(0,"Error","Do you know whats funny?", "You don't have internet connection")
    }
}