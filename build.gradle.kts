plugins {

    id("org.jetbrains.kotlin.jvm") version "2.0.0-RC2" // "2.0.0-Beta4"
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.0-RC2"
    id("org.jetbrains.compose") version "1.6.10-beta03"
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

    // The kotlin-stdlib-jdk[78] for version 1.8.20 are being added from somewhere, without the following line; but it seems to be optional
    implementation(platform("org.jetbrains.kotlin:kotlin-bom:2.0.0-RC2"))

    // Use the (at the time) latest version of kotlinx-coroutines
    implementation(platform("org.jetbrains.kotlinx:kotlinx-coroutines-bom:1.8.0"))
    implementation(compose.desktop.currentOs)
}
