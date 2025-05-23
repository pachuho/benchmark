import com.pachuho.benchmark.setNamespace

plugins {
    id("benchmark.android.library")
}

android {
    setNamespace("core.datastore")
}

dependencies {
    implementation(projects.core.model)
    implementation(libs.androidx.datastore)

    testImplementation(libs.junit)
    testImplementation(libs.kotlin.test)
}