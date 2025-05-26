package com.pachuho.benchmark.core.designsystem.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil.CoilImage
import com.skydoves.landscapist.components.rememberImageComponent
import com.skydoves.landscapist.placeholder.placeholder.PlaceholderPlugin

@Composable
fun BenchmarkImage(
    modifier: Modifier = Modifier,
    imageUrl: () -> Any?,
    placeholderLoading: Painter,
    placeholderFailure: Painter
) {
    CoilImage(
        modifier = modifier,
        imageModel = { imageUrl },
        component = rememberImageComponent {
            +PlaceholderPlugin.Loading(placeholderLoading)
            +PlaceholderPlugin.Failure(placeholderFailure)
        },
        imageOptions = ImageOptions(
            contentScale = ContentScale.Crop,
            alignment = Alignment.Center
        )
    )
}

