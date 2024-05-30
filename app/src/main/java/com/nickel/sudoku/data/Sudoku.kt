package com.nickel.sudoku.data


data class Sudoku(
    val grid: List<Cell>,
    val solution: List<Int>
)

data class Cell(
    val fieldType: FieldType,
    val entry: Int
)

enum class FieldType {
    EMPTY,
    GIVEN,
    HINT,
    ENTRY,
    ERROR
}

data class Hint(
    val index: Int,
    val number: Int
)

