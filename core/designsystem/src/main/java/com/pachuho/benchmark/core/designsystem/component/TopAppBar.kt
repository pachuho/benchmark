package com.pachuho.benchmark.core.designsystem.component

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pachuho.benchmark.core.designsystem.theme.BenchmarkTheme

@Composable
fun BenchmarkTopAppBar(
    @StringRes titleRes: Int,
    modifier: Modifier = Modifier,
    navigationType: TopAppBarNavigationType = TopAppBarNavigationType.Back,
    contentColor: Color = MaterialTheme.colorScheme.onSurface,
    containerColor: Color = MaterialTheme.colorScheme.background,
    actionButtons: @Composable () -> Unit = {},
    onNavigationClick: () -> Unit = {},
) {
    CompositionLocalProvider(LocalContentColor provides contentColor) {
        val icon: @Composable (Modifier, imageVector: ImageVector) -> Unit =
            { modifier, imageVector ->
                IconButton(
                    onClick = onNavigationClick,
                    modifier = modifier.size(48.dp)
                ) {
                    Icon(
                        imageVector = imageVector,
                        contentDescription = "Navigation icon",
                    )
                }
            }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(containerColor)
                .pointerInput(Unit) { /* no-op */ }
                .padding(bottom = 10.dp)
                .then(modifier)
        ) {
            if (navigationType == TopAppBarNavigationType.Back) {
                icon(
                    Modifier.align(Alignment.CenterStart),
                    Icons.AutoMirrored.Filled.ArrowBack
                )
            } else if (navigationType == TopAppBarNavigationType.None) {
                Spacer(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .size(48.dp) // IconButton 기본 크기 맞춤
                )
            }
            Row(Modifier.align(Alignment.CenterEnd)) {
                actionButtons()
                if (navigationType == TopAppBarNavigationType.Close) {
                    icon(
                        Modifier,
                        Icons.Filled.Close
                    )
                }
            }
            Text(
                text = stringResource(id = titleRes),
                style = BenchmarkTheme.typography.titleMediumB,
                modifier = Modifier.align(Alignment.Center)
            )
            HorizontalDivider(
                modifier = Modifier
                    .align(Alignment.BottomCenter),
                thickness = 1.dp,
                color = Color.LightGray
            )
        }
    }
}

enum class TopAppBarNavigationType { Back, Close, None }

@Preview
@Composable
private fun BenchmarkTopAppBarPreviewNone() {
    BenchmarkTopAppBar(
        titleRes = android.R.string.untitled,
        navigationType = TopAppBarNavigationType.None
    )
}


@Preview
@Composable
private fun BenchmarkTopAppBarPreviewBack() {
    BenchmarkTopAppBar(
        titleRes = android.R.string.untitled,
        navigationType = TopAppBarNavigationType.Back
    )
}

@Preview
@Composable
private fun BenchmarkTopAppBarPreviewClose() {
    BenchmarkTopAppBar(
        titleRes = android.R.string.untitled,
        navigationType = TopAppBarNavigationType.Close
    )
}
