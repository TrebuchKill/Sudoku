package de.hubar

import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import de.hubar.sudoku.ui.rememberApp
import de.hubar.sudoku.ui.App

fun main() = application(exitProcessOnExit = true) {

    var running: Boolean by remember { mutableStateOf(true) }
    var app by rememberApp()
    if(running)
    {
        Window(onCloseRequest =  { running = false }) {

            MaterialTheme {

                App(app) { app = it }
            }
        }
    }
}
