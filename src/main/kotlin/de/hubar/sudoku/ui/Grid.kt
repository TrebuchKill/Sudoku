package de.hubar.sudoku.ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.hubar.sudoku.data.Cell as CellData
import de.hubar.sudoku.data.Grid as GridData

// TODO: How does the code react to different display scaling settings?

private val valueSize = 48.sp
private val guessSize = 12.sp
private val boxSpacer = 2.dp

@Composable
fun Grid(grid: GridData) = MaterialTheme {

    Column(Modifier.padding(4.dp).background(Color.Black)) {

        repeat(3) { col ->

            Row {

                repeat(3) { row ->

                    Box(grid.box(col, row))
                    if(row < 2)
                    {
                        Spacer(Modifier.width(boxSpacer))
                    }
                }
            }
            if(col < 2)
            {
                Spacer(Modifier.height(boxSpacer))
            }
        }
    }
}

@Composable
fun Box(box: GridData.Box) = MaterialTheme {

    Column {

        repeat(3) { row ->

            Row {

                repeat(3) { col ->

                    Cell(box[col, row]) { println("Clicked on ($col, $row)") }
                }
            }
        }
    }
}

@Composable
fun Cell(cell: CellData, onUpdate: () -> Unit) = MaterialTheme {

    val modifier = Modifier.background(Color.White).size(64.dp).border(1.dp, Color.Gray)

    when(cell)
    {
        CellData.Empty -> CellEmpty(modifier, onUpdate)
        is CellData.Value -> CellValue(modifier, cell, onUpdate)
        is CellData.Guess -> CellGuess(modifier, cell, onUpdate)
    }
}

@Composable
private fun CellEmpty(modifier: Modifier, onUpdate: () -> Unit) =
    Box(modifier.clickable(onClick = onUpdate))

@Composable
private fun CellValue(modifier: Modifier, cell: CellData.Value, onUpdate: () -> Unit) =
    Box(modifier.clickable(onClick = onUpdate), contentAlignment = Alignment.Center) {

        Text(cell.value.toString(), fontSize = valueSize)
    }

@Composable
private fun CellGuess(modifier: Modifier, cell: CellData.Guess, onUpdate: () -> Unit) =
    Column(modifier.fillMaxHeight(1f).padding(8.dp, 4.dp).clickable(onClick = onUpdate), verticalArrangement = Arrangement.SpaceBetween) {

        Row(Modifier.fillMaxWidth(1f), horizontalArrangement = Arrangement.SpaceBetween) {

            Text(if(1 in cell.values) "1" else "", fontSize = guessSize)
            Text(if(2 in cell.values) "2" else "", fontSize = guessSize)
            Text(if(3 in cell.values) "3" else "", fontSize = guessSize)
        }
        Row(Modifier.fillMaxWidth(1f), horizontalArrangement = Arrangement.SpaceBetween) {

            Text(if(4 in cell.values) "4" else "", fontSize = guessSize)
            Text(if(5 in cell.values) "5" else "", fontSize = guessSize)
            Text(if(6 in cell.values) "6" else "", fontSize = guessSize)
        }
        Row(Modifier.fillMaxWidth(1f), horizontalArrangement = Arrangement.SpaceBetween) {

            Text(if(7 in cell.values) "7" else "", fontSize = guessSize)
            Text(if(8 in cell.values) "8" else "", fontSize = guessSize)
            Text(if(9 in cell.values) "9" else "", fontSize = guessSize)
        }
    }

@Composable
@Preview
fun CellPreview()
{
    val grid = remember {
        val grid = GridData()

        grid[2, 0] = grid[2, 0].guess(1).guess(3).guess(5).guess(7).guess(9)

        repeat(9) {

            grid[it, it] = grid[it, it].value(it + 1)
        }

        grid
    }
    MaterialTheme {

        Grid(grid)
    }
}
