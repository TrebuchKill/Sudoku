plugins {

    kotlin("jvm") version libs.versions.kotlin

    // Plugin to interact with the compose compiler, which is built-in into Kotlin since Kotlin Version 2.0.0-RC2
    // same version as Kotlin version
    kotlin("plugin.compose") version libs.versions.kotlin

    // Actual Compose Library Version
    id("org.jetbrains.compose") version libs.versions.compose
}

repositories {

    mavenCentral()
    google() // Since compose 1.6.0 a hard requirement :-(
}

java.toolchain.languageVersion.set(JavaLanguageVersion.of(21))

compose.desktop.application {

    this.mainClass = "de.hubar.SudokuKt"
}

dependencies {

    constraints {

        // has module-info
        implementation("org.jetbrains:annotations:[23.0,)")
    }

    // The kotlin-stdlib-jdk[78] for version 1.8.20 (the version may be inaccurate in the future) are being added from somewhere, without the following line; but this line seems to be optional (no harm done when missing)
    implementation(platform(libs.kotlin.bom))

    // Use the (at the time) latest version of kotlinx-coroutines
    implementation(platform(libs.kotlinx.coroutines))
    implementation(compose.desktop.currentOs)
}
