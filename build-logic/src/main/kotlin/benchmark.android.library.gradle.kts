import com.android.build.gradle.LibraryExtension
import com.pachuho.benchmark.configureCoroutineAndroid
import com.pachuho.benchmark.configureHiltAndroid
import com.pachuho.benchmark.configureKotlinAndroid
import org.gradle.kotlin.dsl.configure

plugins {
    id("com.android.library")
    id("benchmark.verify.detekt")
}

extensions.configure<LibraryExtension> {
    defaultConfig.targetSdk = 35
}

configureKotlinAndroid()
configureCoroutineAndroid()
configureHiltAndroid()
