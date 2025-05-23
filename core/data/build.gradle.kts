import com.pachuho.benchmark.setNamespace
import java.util.Properties
import java.io.FileInputStream

plugins {
    id("benchmark.android.library")
    id("kotlinx-serialization")
}

val localProperties = Properties().apply {
    load(FileInputStream(rootProject.file("local.properties")))
}

android {
    setNamespace("core.data")

    buildFeatures {
        buildConfig = true
    }

    defaultConfig {
        buildConfigField(
            "String",
            "API_BASE_URL",
            "\"${localProperties["API_BASE_URL"]}\""
        )
    }
}

dependencies {
    implementation(projects.core.model)
    implementation(projects.core.domain)
    implementation(projects.core.datastore)

    implementation(libs.retrofit.core)
    implementation(libs.retrofit.kotlin.serialization)
    implementation(libs.okhttp.logging)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.datetime)
}