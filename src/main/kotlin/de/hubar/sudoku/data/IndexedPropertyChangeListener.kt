package de.hubar.sudoku.data

import java.beans.IndexedPropertyChangeEvent
import java.beans.PropertyChangeListener

fun interface IndexedPropertyChangeListener
{
    fun propertyChange(evt: IndexedPropertyChangeEvent)
}

fun IndexedPropertyChangeListener.wrap() : PropertyChangeListener =
    PropertyChangeListener { evt ->

        if(evt is IndexedPropertyChangeEvent)
        {
            this@wrap.propertyChange(evt)
        }
    }
