package de.hubar.sudoku.ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import de.hubar.sudoku.data.Grid as GridData

@Composable
fun Box(box: GridData.Box, onUpdate: (Int, Int) -> Unit) = MaterialTheme {

    Column {

        repeat(3) { row ->

            Row {

                repeat(3) { col ->

                    Cell(box[col, row]) { onUpdate(col, row) }
                }
            }
        }
    }
}

@Composable
@Preview
fun BoxPreview()
{
    val grid = remember {
        val grid = GridData()

        grid[2, 0] = grid[2, 0].guess(1, 3, 5, 7, 9)

        repeat(9) {

            grid[it, it] = grid[it, it].value(it + 1)
        }

        grid
    }
    MaterialTheme {

        Box(grid.box(0, 0)) { _, _ -> }
    }
}
