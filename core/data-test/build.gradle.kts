import com.pachuho.benchmark.setNamespace

plugins {
    id("benchmark.android.library")
    alias(libs.plugins.kotlin.serialization)
}

android {
    setNamespace("core.data_test")

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(projects.core.data)
    implementation(projects.core.datastore)
    implementation(projects.core.eventbus)

    implementation(libs.kotlinx.serialization.json)
    implementation(libs.okhttp)
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.kotlin.serialization)

    // Test
    testImplementation(libs.junit)
    testImplementation(libs.coroutines.test)
    testImplementation(libs.mockwebserver)
}