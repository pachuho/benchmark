import com.pachuho.benchmark.setNamespace

plugins {
    id("benchmark.android.library")
}

android {
    setNamespace("core.eventbus")
}

dependencies {
    implementation(projects.core.model)
}