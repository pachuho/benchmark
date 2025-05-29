import com.pachuho.benchmark.setNamespace

plugins {
    id("benchmark.android.library")
}

android {
    setNamespace("core.firebase")
}

dependencies {
    implementation(projects.core.datastore)
    implementation(projects.core.eventbus)
    implementation(projects.core.model)

    api(platform(libs.firebase.bom))
    api(libs.firebase.messaging)
}