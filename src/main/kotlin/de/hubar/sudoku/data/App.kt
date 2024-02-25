package de.hubar.sudoku.data

import java.awt.event.KeyEvent
import kotlin.math.max

class App(
    val grid: Grid,
    val selection: Digit?,
    val guessing: Boolean,
    val previousGrids: List<Grid>)
{
    constructor() : this(Grid(), null, false, listOf())

    fun onSelectionClick(selection: Digit?) : App =
        copy(selection = selection)

    fun onGridClick(x: Int, y: Int) : App
    {
        val func: Cell.(value: Digit?) -> Cell =
            if(guessing)
            {
                { if(it != null) this.guess(it) else this }
            }
            else
            {
                {
                    if(it != null)
                    {
                        this.value(it)
                    }
                    else
                    {
                        this.clear()
                    }
                }
            }

        val cur = grid[x, y]
        val mutant = cur.func(selection)

        return if(cur != mutant)
        {
            copy(grid = grid.copy(Triple(x, y, mutant)), previousGrids = previousGrids + grid)
        }
        else
        {
            this
        }
    }

    fun onToggleGuessing(value: Boolean) : App =
        copy(guessing = value)

    fun onKeyPress(evt: KeyEvent) : App? = when(evt.id)
    {
        KeyEvent.KEY_RELEASED -> when(evt.extendedKeyCode) // extended key code may be a bad choice
        {
            KeyEvent.VK_G -> copy(guessing = !guessing)
            KeyEvent.VK_SPACE, KeyEvent.VK_BACK_SPACE, KeyEvent.VK_0, KeyEvent.VK_NUMPAD0 -> copy(selection = null)
            KeyEvent.VK_1, KeyEvent.VK_NUMPAD1 -> copy(selection = Digit(1))
            KeyEvent.VK_2, KeyEvent.VK_NUMPAD2 -> copy(selection = Digit(2))
            KeyEvent.VK_3, KeyEvent.VK_NUMPAD3 -> copy(selection = Digit(3))
            KeyEvent.VK_4, KeyEvent.VK_NUMPAD4 -> copy(selection = Digit(4))
            KeyEvent.VK_5, KeyEvent.VK_NUMPAD5 -> copy(selection = Digit(5))
            KeyEvent.VK_6, KeyEvent.VK_NUMPAD6 -> copy(selection = Digit(6))
            KeyEvent.VK_7, KeyEvent.VK_NUMPAD7 -> copy(selection = Digit(7))
            KeyEvent.VK_8, KeyEvent.VK_NUMPAD8 -> copy(selection = Digit(8))
            KeyEvent.VK_9, KeyEvent.VK_NUMPAD9 -> copy(selection = Digit(9))
            else -> null
        }
        else -> null
    }

    fun onGoBack() : App
    {
        val grids = ArrayList<Grid>(max(previousGrids.size - 1, 1))
        var lastGrid: Grid? = null
        previousGrids.filterIndexedTo(grids) { i, it ->

            when(i)
            {
                previousGrids.size - 1 -> { lastGrid = it; false }
                else -> true
            }
        }
        return copy(grid = checkNotNull(lastGrid) { "Called onGoBack while no previous entry exists" }, previousGrids = grids)
    }

    fun copy(
        grid: Grid = this.grid,
        selection: Digit? = this.selection,
        guessing: Boolean = this.guessing,
        previousGrids: List<Grid> = this.previousGrids) : App =
        App(grid, selection, guessing, previousGrids)
}
