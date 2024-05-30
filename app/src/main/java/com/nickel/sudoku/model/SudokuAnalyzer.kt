package com.nickel.sudoku.model

import com.nickel.sudoku.data.Cell
import com.nickel.sudoku.data.FieldType

class SudokuAnalyzer {

    fun getFieldEntry(grid: List<Cell>, index: Int): Int = grid[index].entry

    fun isFieldEntryMutable(grid: List<Cell>, index: Int): Boolean = grid[index].fieldType != FieldType.GIVEN

    fun isFieldEntryRemovable(grid: List<Cell>, index: Int): Boolean =
        grid[index].fieldType == FieldType.ENTRY || grid[index].fieldType == FieldType.ERROR || grid[index].fieldType == FieldType.HINT

    fun updateSudokuGrid(grid: List<Cell>, index: Int, entry: Int, solution: List<Int>): List<Cell> {
        return grid.toMutableList().also {
            if (it[index].fieldType != FieldType.GIVEN) {
                it[index] = Cell(
                    fieldType = when (entry) {
                        0 -> FieldType.EMPTY
                        solution[index] -> FieldType.ENTRY
                        else -> FieldType.ERROR
                    },
                    entry = entry
                )
            }
            it.toList()
        }
    }
}