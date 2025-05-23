package com.pachuho.benchmark.core.designsystem.component

import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pachuho.benchmark.core.designsystem.theme.BenchmarkTheme

@Composable
fun BenchmarkButton(
    modifier: Modifier = Modifier,
    @StringRes textRes: Int,
    buttonType: ButtonType = ButtonType.Filled,
    color: Color = MaterialTheme.colorScheme.onSurface,
    onClick: () -> Unit
) {
    val shape = RoundedCornerShape(12.dp)

    val (containerColor, contentColor, border) = when(buttonType) {
        ButtonType.Filled ->
            Triple(color, Color.White, null)
        ButtonType.OutLined ->
            Triple(Color.Transparent, color, BorderStroke(1.dp, color))
    }

    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            disabledContainerColor = containerColor,
            contentColor = contentColor,
            disabledContentColor = contentColor
        ),
        shape = shape,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        border = border,
    ) {
        Text(
            text = stringResource(textRes),
            style = BenchmarkTheme.typography.titleMediumR,
        )
    }
}

enum class ButtonType { Filled, OutLined }

@Preview
@Composable
private fun BenchmarkButtonPreviewFilled() {
    BenchmarkTheme {
        BenchmarkButton(
            textRes = android.R.string.untitled
        ) { }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun BenchmarkButtonPreviewOutLined() {
    BenchmarkTheme {
        BenchmarkButton(
            textRes = android.R.string.untitled,
            buttonType = ButtonType.OutLined
        ) { }
    }
}