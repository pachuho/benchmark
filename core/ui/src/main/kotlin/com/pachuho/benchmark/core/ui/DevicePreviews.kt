package com.pachuho.benchmark.core.ui

import androidx.compose.ui.tooling.preview.Preview

@Preview(name = "phone", device = "spec:width=360dp,height=640dp,dpi=480", showBackground = true)
@Preview(name = "landscape", device = "spec:width=640dp,height=360dp,dpi=480", showBackground = true)
@Preview(name = "tablet", device = "spec:width=1280dp,height=800dp,dpi=480", showBackground = true)
annotation class DevicePreviews
