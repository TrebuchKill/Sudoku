package de.hubar.sudoku.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.TooltipArea
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.hubar.sudoku.data.Digit
import de.hubar.sudoku.data.App as AppData

// https://foso.github.io/Jetpack-Compose-Playground/desktop/overview/
// Putting this helpful link in here for reference

@Composable
private fun AppTooltip(text: String) =
    Surface(border = BorderStroke(Dp.Hairline, Color.Black), shape = RoundedCornerShape(8.dp), elevation = 10.dp) {

        Text(text, Modifier.padding(8.dp))
    }

@OptIn(ExperimentalFoundationApi::class) // Required for TooltipArea
@Composable
fun App(app: AppData, setApp: (AppData) -> Unit) = MaterialTheme {

    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        Text("Previous Grids: ${app.previousGrids.size}")
        TooltipArea(tooltip = { AppTooltip("Reset") }) {

            Button({ setApp(AppData()) }) {

                Text("\u21bb")
            }
        }
        TooltipArea(tooltip = { AppTooltip("Back") }) {

            Button({ setApp(app.onGoBack()) }, enabled = app.previousGrids.isNotEmpty()) {

                Text("\ud83e\udc70")
            }
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
