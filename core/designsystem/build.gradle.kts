import com.pachuho.benchmark.setNamespace

plugins {
    id("benchmark.android.library")
    id("benchmark.android.compose")
}

android {
    setNamespace("core.designsystem")
}

dependencies {
    implementation(libs.androidx.appcompat)

    implementation(libs.landscapist.bom)
    implementation(libs.landscapist.coil)
    implementation(libs.landscapist.placeholder)
}