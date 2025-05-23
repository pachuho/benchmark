import com.pachuho.benchmark.setNamespace

plugins {
    id("benchmark.android.library")
    id("benchmark.android.compose")
}

android {
    setNamespace("core.ui")
}

dependencies {
}