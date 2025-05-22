import com.pachuho.benchmark.setNamespace

plugins {
    id("benchmark.android.library")
    id("benchmark.android.compose")
    alias(libs.plugins.kotlin.serialization)
}

android {
    setNamespace("core.navigation")
}

dependencies {
    implementation(libs.kotlinx.serialization.json)
}