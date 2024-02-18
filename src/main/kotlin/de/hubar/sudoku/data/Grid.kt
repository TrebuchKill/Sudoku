package de.hubar.sudoku.data

import java.beans.PropertyChangeListener
import java.beans.PropertyChangeSupport
import java.util.*
import kotlin.Comparator

class Grid private constructor(private val grid: Array<Cell>)
{
    companion object
    {
        private const val WIDTH: Int = 9
        private const val HEIGHT: Int = 9
        private const val SIZE: Int = WIDTH * HEIGHT

        private val WIDTH_RANGE: IntRange = 0..<WIDTH
        private val HEIGHT_RANGE: IntRange = 0..<HEIGHT
        private val SIZE_RANGE: IntRange = 0..<SIZE

        private fun coordsToIndex(x: Int, y: Int) : Int
        {
            require(x in WIDTH_RANGE)
            require(y in HEIGHT_RANGE)
            return x + (y * WIDTH)
        }

        private fun indexToCoords(i: Int) : Pair<Int, Int>
        {
            require(i in SIZE_RANGE)

            val x = i % WIDTH
            val y = (i - x) / WIDTH

            return x to y
        }

        private val indexCellComparator = Comparator<Pair<Int, Cell>> { (li, _), (ri, _) ->

            li.compareTo(ri)
        }
    }

    class Box private constructor(private val offsetX: Int, private val offsetY: Int, private val grid: Grid, marker: Unit)
    {
        companion object
        {
            private val BOX_RANGE: IntRange = 0..<3
        }

        constructor(boxX: Int, boxY: Int, grid: Grid)
            : this(boxX * 3,boxY * 3, grid, Unit)
        {
            require(boxX in BOX_RANGE)
            require(boxY in BOX_RANGE)
        }

        operator fun get(x: Int, y: Int) : Cell
        {
            require(x in BOX_RANGE)
            require(y in BOX_RANGE)
            return grid[offsetX + x, offsetY + y]
        }

        operator fun set(x: Int, y: Int, value: Cell)
        {
            require(x in BOX_RANGE)
            require(y in BOX_RANGE)
            grid[offsetX + x, offsetY + y] = value
        }
    }

    class Row(val y: Int, val grid: Grid)
    {
        init
        {
            require(y in HEIGHT_RANGE)
        }

        operator fun get(x: Int) : Cell
        {
            require(x in WIDTH_RANGE)
            return grid[x, y]
        }

        operator fun set(x: Int, value: Cell)
        {
            require(x in WIDTH_RANGE)
            grid[x, y] = value
        }
    }

    class Column(val x: Int, val grid: Grid)
    {
        init
        {
            require(x in WIDTH_RANGE)
        }

        operator fun get(y: Int) : Cell
        {
            require(y in HEIGHT_RANGE)
            return grid[x, y]
        }

        operator fun set(y: Int, value: Cell)
        {
            require(y in HEIGHT_RANGE)
            grid[x, y] = value
        }
    }

    // private val grid = Array<Cell>(SIZE) { Cell.Empty }
    private val pcs = PropertyChangeSupport(this)

    constructor() : this(Array(SIZE) { Cell.Empty })

    // Seems to have the desired effect: Resolve the ambiguous case of no argument.
    fun copy() = copyImpl(sortedSetOf())

    fun copy(vararg changes: Triple<Int, Int, Cell>) =
        copyImpl(
            changes.map { (x, y, v) -> coordsToIndex(x, y) to v }
                .toSortedSet(indexCellComparator))

    fun copy(vararg changes: Pair<Int, Cell>) =
        copyImpl(
            changes.toSortedSet(indexCellComparator))

    private fun copyImpl(changes: SortedSet<Pair<Int, Cell>>) : Grid
    {
        if(changes.isNotEmpty())
        {
            println("Copy with Changes: $changes")
        }
        return Grid(Array(SIZE) { i ->

            val firstIndex = changes.firstOrNull()?.first

            when
            {
                firstIndex == i -> changes.removeFirst().also { check(it.first == firstIndex) }.second
                firstIndex == null || firstIndex > i -> grid[i]
                else -> throw IllegalStateException("Grid copy failed: An index in the pending changes exists ($firstIndex), which is lower the current index ($i).")
            }
        })
    }

    operator fun get(i: Int) : Cell
    {
        require(i in grid.indices)
        return grid[i]
    }

    operator fun get(x: Int, y: Int) : Cell
    {
        require(x in WIDTH_RANGE)
        require(y in HEIGHT_RANGE)
        return grid[coordsToIndex(x, y)]
    }

    operator fun set(i: Int, value: Cell)
    {
        require(i in grid.indices)
        val old = grid[i]
        grid[i] = value
        pcs.fireIndexedPropertyChange(null, i, old, value)
    }

    operator fun set(x: Int, y: Int, value: Cell)
    {
        // grid[coordsToIndex(x, y)] = value
        this[coordsToIndex(x, y)] = value
    }

    fun box(boxX: Int, boxY: Int) : Box =
        Box(boxX, boxY, this)

    fun row(y: Int) : Row =
        Row(y, this)

    fun column(x: Int) : Column =
        Column(x, this)

    fun addPropertyChangeListener(listener: PropertyChangeListener) : Unit =
        pcs.addPropertyChangeListener(listener)

    fun addPropertyChangeListener(propertyName: String, listener: PropertyChangeListener) : Unit =
        pcs.addPropertyChangeListener(propertyName, listener)

    fun addIndexedPropertyChangeListener(listener: IndexedPropertyChangeListener) : PropertyChangeListener =
        listener.wrap().also(this::addPropertyChangeListener)

    fun addIndexedPropertyChangeListener(propertyName: String, listener: IndexedPropertyChangeListener) : PropertyChangeListener =
        listener.wrap().also { addPropertyChangeListener(propertyName, it) }

    fun removePropertyChangeListener(listener: PropertyChangeListener) : Unit =
        pcs.removePropertyChangeListener(listener)

    fun removePropertyChangeListener(propertyName: String, listener: PropertyChangeListener) : Unit =
        pcs.removePropertyChangeListener(propertyName, listener)
}
