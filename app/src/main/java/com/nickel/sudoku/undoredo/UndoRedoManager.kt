package com.nickel.sudoku.undoredo

import java.util.Stack

class UndoRedoManager(
    private val undoActions: Stack<UndoRedoAction>,
    private val redoActions: Stack<UndoRedoAction>
) {
    fun addUndoAction(action: UndoRedoAction) {
        undoActions.push(action)
    }

    fun undoAction(): UndoRedoAction? {
        if (undoActions.isNotEmpty()) {
            val action = undoActions.pop()
            redoActions.push(action)
            return action
        }
        return null
    }

    fun redoAction(): UndoRedoAction? {
        if (redoActions.isNotEmpty()) {
            val action = redoActions.pop()
            undoActions.push(action)
            return action
        }
        return null
    }

    fun undoActionsAvailable(): Boolean = undoActions.isNotEmpty()

    fun redoActionsAvailable(): Boolean = redoActions.isNotEmpty()
}