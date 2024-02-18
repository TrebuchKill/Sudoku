package de.hubar

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import de.hubar.data.Grid

fun main() = application(exitProcessOnExit = true) {

    var running: Boolean by remember { mutableStateOf(true) }
    if(running)
    {
        Window(onCloseRequest =  { running = false }) {

            MaterialTheme {

                Text("Hello World")
            }
        }
    }
}
