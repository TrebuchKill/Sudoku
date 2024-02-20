package de.hubar.sudoku.data

sealed interface Cell
{
    val isBlank: Boolean
    val isEmpty: Boolean

    fun clear(): Cell =
        Empty

    fun value(value: Int): Cell =
        Value.of(value)

    fun value(value: Digit) : Cell =
        Value.of(value)

    fun guess(value: Int): Cell =
        guess(Digit(value))

    // UB if a value is repeated... should probably be a KDoc
    fun guess(vararg values: Int) : Cell
    {
        require(values.isNotEmpty()) { "A call to guess requires at least one value" }
        return values.fold(this, Cell::guess)
    }

    fun guess(value: Digit) : Cell =
        Guess(value)

    data object Empty: Cell
    {
        override val isBlank: Boolean
            get() = true

        override val isEmpty: Boolean
            get() = true

        override fun toString(): String =
            "Empty"
    }

    class Value private constructor(val value: Digit) : Cell
    {
        companion object
        {
            private val VALUES = Array(9) { i -> Value(Digit(i + 1)) }

            fun of(value: Int) : Value =
                VALUES[value - 1] // value 1 is at offset 0, value 2 at offset 1, ...

            fun of(value: Digit) : Value =
                VALUES[value.value - 1]
        }

        override val isBlank: Boolean
            get() = false
        override val isEmpty: Boolean
            get() = false

        override fun toString(): String =
            "Value($value)"
    }

    data class Guess(val values: Set<Digit>) : Cell
    {
        constructor(value: Digit) : this(setOf(value))

        override fun guess(value: Digit) : Cell =
            if(value in values)
            {
                Guess(values - value)
            }
            else
            {
                Guess(values + value)
            }

        override val isBlank: Boolean
            get() = false
        override val isEmpty: Boolean
            get() = true

        override fun toString(): String =
            "Guess($values)"
    }
}
