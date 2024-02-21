package de.hubar.sudoku.data

import java.awt.event.KeyEvent

class App(
    val grid: Grid,
    val selection: Digit?,
    val guessing: Boolean)
{
    constructor() : this(Grid(), null, false)

    fun onSelectionClick(selection: Digit?) : App =
        copy(selection = selection)

    fun onGridClick(x: Int, y: Int) : App
    {
        val func: Cell.(value: Digit?) -> Cell
        if(guessing)
        {
            func = { it -> if(it != null) this.guess(it) else this }
        }
        else
        {
            func = {
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
        return copy(grid = grid.copy(Triple(x, y, grid[x, y].func(selection))))
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

    fun copy(grid: Grid = this.grid, selection: Digit? = this.selection, guessing: Boolean = this.guessing) : App =
        App(grid, selection, guessing)
}
