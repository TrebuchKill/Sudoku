package de.hubar.sudoku.data

@JvmInline
value class Digit(val value: Int) : Comparable<Digit>
{
    init
    {
        require(value in 1..9)
    }

    override fun toString(): String =
        value.toString()

    override operator fun compareTo(other: Digit): Int =
        value.compareTo(other.value)

    operator fun compareTo(other: Int): Int =
        value.compareTo(other)
}
