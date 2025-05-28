import com.pachuho.benchmark.setNamespace

plugins {
    id("benchmark.android.library")
    id("kotlinx-serialization")
}

android {
    setNamespace("core.domain")
}

dependencies {
    implementation(projects.core.model)

    implementation(libs.retrofit.core)
    implementation(libs.kotlinx.serialization.json)
}