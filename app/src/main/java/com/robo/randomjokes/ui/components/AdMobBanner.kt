package com.robo.randomjokes.ui.components

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.robo.randomjokes.R

@Composable
fun AdMobBanner() {

    val randomJokesBannerId = stringResource(id = R.string.randomjokesbanner_id)

    AndroidView(factory = { context ->
        AdView(context).apply {
            setAdSize(AdSize.BANNER)
            adUnitId = randomJokesBannerId
            loadAd(AdRequest.Builder().build())
        }
    }, modifier = Modifier.fillMaxWidth().fillMaxHeight())
}