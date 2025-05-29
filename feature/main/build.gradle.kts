import com.pachuho.benchmark.setNamespace

plugins {
    id("benchmark.android.feature")
}

android {
    setNamespace("feature.main")

    defaultConfig {
        testInstrumentationRunner =
            "com.pachuho.app.core.testing.runner.BenchmarkTestRunner"
    }
}

dependencies {
    implementation(projects.feature.login)
    implementation(projects.feature.device)
    implementation(projects.core.data)
    implementation(projects.core.eventbus)
    androidTestImplementation(projects.core.testing)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.lifecycle.runtimeCompose)
    implementation(libs.androidx.lifecycle.viewModelCompose)
    implementation(libs.kotlinx.immutable)
    androidTestImplementation(libs.hilt.android.testing)
    kspAndroidTest(libs.hilt.android.compiler)
}