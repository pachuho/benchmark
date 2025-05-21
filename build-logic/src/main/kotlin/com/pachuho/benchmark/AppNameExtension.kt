package com.pachuho.benchmark

import org.gradle.api.Project
import kotlin.apply

fun Project.setNamespace(name: String) {
    androidExtension.apply {
        namespace = "com.pachuho.benchmark.$name"
    }
}