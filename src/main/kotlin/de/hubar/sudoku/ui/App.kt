package de.hubar.sudoku.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.hubar.sudoku.data.Digit
import de.hubar.sudoku.data.App as AppData

// https://foso.github.io/Jetpack-Compose-Playground/desktop/overview/
// Putting this helpful link in here for reference

@Composable
fun App(app: AppData, setApp: (AppData) -> Unit) = MaterialTheme {

    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        Text("Previous Grids: ${app.previousGrids.size}")
        Button({ setApp(AppData()) }) {

            Text("Reset")
        }
        Button({ setApp(app.onGoBack()) }, enabled = app.previousGrids.isNotEmpty()) {

            Text("Back")
        }
        Grid(app.grid) { x, y -> setApp(app.onGridClick(x, y)) }
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
                Button(modifier = Modifier.size(48.dp), colors = buttonColors, onClick = { setApp(app.onSelectionClick(ret)) }) {

                    Text(when (i)
                    {
                        0 -> "\u02FD"
                        else -> i.toString()
                    }, fontSize = 24.sp)
                }
            }
        }
        Row(verticalAlignment = Alignment.CenterVertically) {

            Switch(app.guessing, { setApp(app.onToggleGuessing(it)) })
            Text("Guessing")
        }
    }
}

@Composable
fun rememberApp() =
    remember { mutableStateOf(AppData()) }
