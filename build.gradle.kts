plugins {

    id("org.jetbrains.kotlin.jvm") version "1.9.22" // "2.0.0-Beta4"
    id("org.jetbrains.compose") version "1.5.12"
}

repositories {

    mavenCentral()
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

    implementation(compose.desktop.currentOs)
}
