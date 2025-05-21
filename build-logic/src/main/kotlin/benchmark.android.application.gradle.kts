import com.android.build.api.dsl.ApplicationExtension
import com.pachuho.benchmark.configureComposeAndroid
import com.pachuho.benchmark.configureHiltAndroid
import com.pachuho.benchmark.configureKotlinAndroid

plugins {
    id("com.android.application")
}

extensions.configure<ApplicationExtension> {
    defaultConfig.targetSdk = 35
}

configureKotlinAndroid()
configureHiltAndroid()
configureComposeAndroid()