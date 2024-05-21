plugins {

    kotlin("jvm") version "2.0.0"

    // Plugin to interact with the compose compiler, which is built-in into Kotlin since Kotlin Version 2.0.0-RC2
    // same version as Kotlin version
    kotlin("plugin.compose") version "2.0.0"

    // Actual Compose Library Version
    id("org.jetbrains.compose") version "1.6.10"
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
    implementation(platform("org.jetbrains.kotlin:kotlin-bom:2.0.0"))

    // Use the (at the time) latest version of kotlinx-coroutines
    implementation(platform("org.jetbrains.kotlinx:kotlinx-coroutines-bom:1.8.1"))
    implementation(compose.desktop.currentOs)
}
