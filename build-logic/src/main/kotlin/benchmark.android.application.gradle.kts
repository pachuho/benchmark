import com.pachuho.benchmark.configureHiltAndroid
import com.pachuho.benchmark.configureKotlinAndroid

plugins {
    id("com.android.application")
}

configureKotlinAndroid()
configureHiltAndroid()
