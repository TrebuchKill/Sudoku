package de.hubar.sudoku.data

@JvmInline
value class Digit(val value: Int)
{
    init
    {
        require(value in 1..9)
    }

    /*operator fun compareTo(rhs: Digit) =
        value.compareTo(rhs.value)

    operator fun compareTo(rhs: Int) =
        value.compareTo(rhs)*/

    override fun toString(): String =
        value.toString()

    operator fun compareTo(other: Digit): Int =
        value.compareTo(other.value)

    operator fun compareTo(other: Int): Int =
        value.compareTo(other)
}