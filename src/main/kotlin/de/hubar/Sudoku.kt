package de.hubar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

import de.hubar.sudoku.data.Grid as GridData
import de.hubar.sudoku.data.Cell as CellData

import de.hubar.sudoku.ui.Grid as GridUi

fun main() = application(exitProcessOnExit = true) {

    var running: Boolean by remember { mutableStateOf(true) }
    var grid: GridData by remember { mutableStateOf(GridData()) }
    if(running)
    {
        Window(onCloseRequest =  { running = false }) {

            MaterialTheme {

                Column {

                    Text("Hello World")
                    GridUi(grid)
                }
            }
        }
    }
}
