package de.hubar.data

private fun requireValue(i: Int) = require(i in 1..9) {

    "A cell can only contain numbers between 1 and 9 (both inclusive), got: $i"
}

sealed interface Cell
{
    val isBlank: Boolean
    val isEmpty: Boolean

    fun clear(): Cell =
        Empty

    fun value(value: Int): Cell =
        Value(value)

    fun guess(value: Int): Cell =
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

    data class Value(val value: Int) : Cell
    {
        init
        {
            requireValue(value)
        }

        override val isBlank: Boolean
            get() = false
        override val isEmpty: Boolean
            get() = false

        override fun toString(): String =
            "Value($value)"
    }

    data class Guess(val values: Set<Int>) : Cell
    {
        init
        {
            requireValue(values.min())
            requireValue(values.max())
        }

        constructor(value: Int) : this(setOf(value))

        override fun guess(value: Int) : Cell =
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
