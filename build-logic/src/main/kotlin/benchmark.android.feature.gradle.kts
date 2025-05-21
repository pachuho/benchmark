import com.pachuho.benchmark.configureComposeAndroid
import com.pachuho.benchmark.configureHiltAndroid
import com.pachuho.benchmark.configureKotlinAndroid
import com.pachuho.benchmark.libs

plugins {
    id("benchmark.android.library")
    id("benchmark.android.compose")
}

configureHiltAndroid()
configureKotlinAndroid()
configureComposeAndroid()