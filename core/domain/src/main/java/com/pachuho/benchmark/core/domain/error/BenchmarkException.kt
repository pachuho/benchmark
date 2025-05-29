package com.pachuho.benchmark.core.domain.error

import androidx.annotation.StringRes

class BenchmarkException(
    @StringRes val messageRes: Int
) : Exception()