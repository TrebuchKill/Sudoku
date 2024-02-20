package de.hubar.sudoku.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.hubar.sudoku.data.Digit
import de.hubar.sudoku.data.App as AppData

@Composable
fun App(app: AppData, onUpdate: (AppData) -> Unit) = MaterialTheme {

    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        Grid(app.grid) { x, y -> onUpdate(app.onGridClick(x, y)) }
        Row {

            repeat(10) { i ->

                var buttonColors = ButtonDefaults.buttonColors()
                if (app.selection == null)
                {
                    if (i == 0)
                    {
                        buttonColors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary)
                    }
                }
                else if (app.selection.value == i)
                {
                    buttonColors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary)
                }

                val ret = if (i == 0) null else Digit(i)
                Button(modifier = Modifier.size(48.dp), colors = buttonColors, onClick = { onUpdate(app.onSelectionClick(ret)) }) {

                    Text(when (i)
                    {
                        0 -> "\u02FD"
                        else -> i.toString()
                    }, fontSize = 24.sp)
                }
            }
        }
    }
}

@Composable
fun rememberApp() =
    remember { mutableStateOf(AppData()) }
