import com.pachuho.benchmark.setNamespace

plugins {
    id("benchmark.android.library")
    id("kotlinx-serialization")
}

android {
    setNamespace("core.model")
}

dependencies {
    api(libs.kotlinx.datetime)
    implementation(libs.kotlinx.serialization.json)
}