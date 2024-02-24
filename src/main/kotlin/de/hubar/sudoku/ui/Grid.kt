package de.hubar.sudoku.ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import de.hubar.sudoku.data.Grid as GridData

// TODO: How does the code react to different display scaling settings?

@Composable
fun Grid(grid: GridData, onUpdate: (Int, Int) -> Unit) = MaterialTheme {

    Column(Modifier.padding(4.dp).background(Color.Black)) {

        repeat(3) { row ->

            Row {

                repeat(3) { col ->

                    Box(grid.box(col, row)) { boxX, boxY -> onUpdate(col * 3 + boxX, row * 3 + boxY) }
                    if(col < 2)
                    {
                        Spacer(Modifier.width(boxSpacer))
                    }
                }
            }
            if(row < 2)
            {
                Spacer(Modifier.height(boxSpacer))
            }
        }
    }
}

@Composable
@Preview
fun GridPreview()
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

        Grid(grid) { _, _ -> }
    }
}
