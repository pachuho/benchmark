import com.pachuho.benchmark.configureCoroutineAndroid
import com.pachuho.benchmark.configureHiltAndroid
import com.pachuho.benchmark.configureKotlinAndroid

plugins {
    id("com.android.library")
    id("benchmark.verify.detekt")
}

configureKotlinAndroid()
configureCoroutineAndroid()
configureHiltAndroid()
