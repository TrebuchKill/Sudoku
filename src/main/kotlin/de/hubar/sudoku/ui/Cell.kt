package de.hubar.sudoku.ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import de.hubar.sudoku.data.Digit
import kotlin.random.Random
import de.hubar.sudoku.data.Cell as CellData

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
    Column(modifier.clickable(onClick = onUpdate).padding(4.dp).fillMaxWidth(1f),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally) {

        Row(Modifier.fillMaxWidth(1f), horizontalArrangement = Arrangement.SpaceBetween) {

            CellGuessText(if(cell.values.any { it.value == 1 }) "1" else "")
            CellGuessText(if(cell.values.any { it.value == 2 }) "2" else "")
            CellGuessText(if(cell.values.any { it.value == 3 }) "3" else "")
        }
        Row(Modifier.fillMaxWidth(1f), horizontalArrangement = Arrangement.SpaceBetween) {

            CellGuessText(if(cell.values.any { it.value == 4 }) "4" else "")
            CellGuessText(if(cell.values.any { it.value == 5 }) "5" else "")
            CellGuessText(if(cell.values.any { it.value == 6 }) "6" else "")
        }
        Row(Modifier.fillMaxWidth(1f), horizontalArrangement = Arrangement.SpaceBetween) {

            CellGuessText(if(cell.values.any { it.value == 7 }) "7" else "")
            CellGuessText(if(cell.values.any { it.value == 8 }) "8" else "")
            CellGuessText(if(cell.values.any { it.value == 9 }) "9" else "")
        }
    }

@Composable
private fun RowScope.CellGuessText(text: String) =
    Text(
        text,
        Modifier.weight(1f),
        lineHeight = TextUnit(16f, TextUnitType.Sp),
        fontSize = guessSize,
        textAlign = TextAlign.Center)

@Composable
@Preview
fun CellPreview()
{
    val empty = remember { CellData.Empty }
    val value = remember { CellData.Value.of(Random.nextInt(0, 9) + 1) }
    val staticGuess = remember { CellData.Guess(setOf(Digit(1), Digit(3), Digit(5), Digit(7), Digit(9))) }
    val fullGuess = remember { CellData.Guess((1..9).map { Digit(it) }.toSet()) }
    val dynamicGuess = remember {

        val number = Random.nextInt(1, 512) // Get a random number from 1 (inclusive) until 512 (exclusive), for at least 1 bit and up to all lowest 9 bits set
        val set = buildSet {

            repeat(9) { i ->

                if(number and (1 shl i) != 0)
                {
                    add(Digit(i + 1))
                }
            }
        }.ifEmpty { setOf(Digit(5)) } // the "if empty lambda" should never be called
        CellData.Guess(set)
    }
    MaterialTheme {

        Column(Modifier.fillMaxWidth()) {

            arrayOf(
                "Empty Cell" to empty,
                "Value" to value,
                "Guess (Cross)" to staticGuess,
                "Guess (Full)" to fullGuess,
                "Guess (Random)" to dynamicGuess).forEach { (label, cell) ->

                Row(verticalAlignment = Alignment.CenterVertically) {

                    Text(label, Modifier.weight(1f), textAlign = TextAlign.Right)
                    Spacer(Modifier.width(8.dp))
                    Cell(cell) {}
                }
            }
        }
    }
}
