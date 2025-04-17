package com.dinakaran.todolistapplication.ui.screens.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.request.ImageRequest
import com.dinakaran.todolistapplication.R

@Composable
fun AppLogo(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val imageLoader = ImageLoader.Builder(context)
        .components { add(GifDecoder.Factory()) }
        .build()

    AsyncImage(
        model = ImageRequest.Builder(context)
            .data(R.drawable.to_do_list_icon)
            .crossfade(true)
            .build(),
        imageLoader = imageLoader,
        contentDescription = "App Logo",
        modifier = modifier
    )
}

