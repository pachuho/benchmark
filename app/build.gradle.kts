plugins {
    id("benchmark.android.application")
}

android {
    namespace = "com.pachuho.benchmark"

    defaultConfig {
        applicationId = "com.pachuho.benchmark"
        versionCode = 1
        versionName = "1.0"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    buildTypes {
        getByName("release") {
            signingConfig = signingConfigs.getByName("debug")
        }
    }
}

dependencies {
    implementation(projects.core.navigation)
    implementation(projects.feature.main)
    implementation(projects.core.designsystem)
}