package de.hubar.sudoku.data

class App(
    val grid: Grid,
    val selection: Digit?,
    val guessing: Boolean)
{
    constructor() : this(Grid(), null, false)

    fun onSelectionClick(selection: Digit?) : App =
        App(grid, selection, guessing)

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

    fun copy(grid: Grid = this.grid, selection: Digit? = this.selection, guessing: Boolean = this.guessing) : App =
        App(grid, selection, guessing)
}
