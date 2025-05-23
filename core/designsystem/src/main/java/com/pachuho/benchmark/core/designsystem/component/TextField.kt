package com.pachuho.benchmark.core.designsystem.component

import androidx.annotation.StringRes
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pachuho.benchmark.core.designsystem.R
import com.pachuho.benchmark.core.designsystem.theme.BenchmarkTheme

@Composable
fun BenchmarkTextField(
    modifier: Modifier = Modifier,
    textType: TextFieldType = TextFieldType.Plain,
    imeAction: ImeAction = ImeAction.Done,
    text: String,
    @StringRes hintRes: Int,
    contentColor: Color = MaterialTheme.colorScheme.onSurface,
    onValueChange: (String) -> Unit,
    onKeyboardDoneAction: () -> Unit = {}
) {
    var passwordVisible by remember { mutableStateOf(false) }

    Box(modifier = modifier) {
        TextField(
            value = text,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, contentColor, shape = RoundedCornerShape(12.dp)),
            placeholder = {
                Text(text = stringResource(hintRes), color = contentColor)
            },
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,

                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
            ),
            singleLine = true,
            trailingIcon = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (text.isNotEmpty()) {
                        if (textType == TextFieldType.Password) {
                            IconButton(
                                onClick = { passwordVisible = !passwordVisible },
                                modifier = Modifier.focusProperties { canFocus = false }
                            ) {
                                Icon(
                                    painter = if (passwordVisible) painterResource(R.drawable.baseline_visibility_24)
                                    else painterResource(R.drawable.baseline_visibility_off_24),
                                    contentDescription = if (passwordVisible) "Hide password" else "Show password",
                                    tint = contentColor
                                )
                            }
                        }

                        IconButton(
                            onClick = { onValueChange("") },
                            modifier = Modifier.focusProperties { canFocus = false }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Clear text",
                                tint = contentColor
                            )
                        }
                    }
                }
            },
            visualTransformation = when {
                textType == TextFieldType.Password && !passwordVisible -> PasswordVisualTransformation()
                else -> VisualTransformation.None
            },
            keyboardOptions = KeyboardOptions(imeAction = imeAction),
            keyboardActions = KeyboardActions(
                onDone = { onKeyboardDoneAction() }
            )
        )
    }
}

enum class TextFieldType { Plain, Password }

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun BenchmarkTextFieldPreviewPlain() {
    var id by remember { mutableStateOf("") }

    BenchmarkTheme {
        BenchmarkTextField(
            hintRes = android.R.string.untitled,
            text = id,
            onValueChange = { id = it },
            onKeyboardDoneAction = {}
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun BenchmarkTextFieldPreviewPassword() {
    var password by remember { mutableStateOf("") }

    BenchmarkTheme {
        BenchmarkTextField(
            hintRes = android.R.string.untitled,
            textType = TextFieldType.Password,
            text = password,
            onValueChange = { password = it },
            onKeyboardDoneAction = {}
        )
    }
}