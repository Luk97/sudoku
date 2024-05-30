package com.nickel.sudoku.undoredo

sealed class UndoRedoAction {
    data class DigitChanged(val index: Int, val digit: Int, val prevDigit: Int): UndoRedoAction()
}