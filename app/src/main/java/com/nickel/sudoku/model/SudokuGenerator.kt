package com.nickel.sudoku.model

import com.nickel.sudoku.data.Cell
import com.nickel.sudoku.data.FieldType
import com.nickel.sudoku.data.Hint
import com.nickel.sudoku.data.Sudoku
import java.util.Stack

class SudokuGenerator {
    // TODO: always succeed and never return null
    fun generateSudoku(): Sudoku {
        val solutionGrid = generateEmptyGrid()
        if (fillGrid(solutionGrid)) {
            val startGrid = solutionGrid.map { it.toMutableList() }.toMutableList()
            val hints = pokeHoles(startGrid, 40)

            // TODO: solution empty
            val grid = mutableListOf<Cell>()
            val solution = mutableListOf<Int>()
            repeat(9) { rowIndex ->
                val row = mutableListOf<Cell>()
                val solutionRow = mutableListOf<Int>()
                repeat(9) { colIndex ->
                    val startEntry = startGrid[rowIndex][colIndex]
                    val solutionEntry = solutionGrid[rowIndex][colIndex]
                    row.add(
                        Cell(
                            fieldType = if (startEntry > 0) {
                                FieldType.GIVEN
                            } else FieldType.EMPTY,
                            entry = if (startEntry > 0) {
                                startEntry
                            } else 0
                        )
                    )
                    solutionRow.add(solutionEntry)
                }
                grid.addAll(row)
                solution.addAll(solutionRow)
            }
            return Sudoku(
                grid = grid,
                solution = solution
            )
        }

        return Sudoku(
            grid = emptyList(),
            solution = emptyList()
        )
    }

    private fun generateEmptyGrid(): List<MutableList<Int>> {
        val grid = mutableListOf<MutableList<Int>>()
        repeat(9) {
            val row = mutableListOf<Int>()
            repeat(9) {
                row.add(0)
            }
            grid.add(row)
        }
        return grid
    }

    private fun fillGrid(grid: List<MutableList<Int>>): Boolean {
        val emptyCell = findEmptyCell(grid) ?: return true
        val (row, col) = emptyCell
        val numbersToTry = (1..9).shuffled()
        for (num in numbersToTry) {
            if (isValidMove(grid, row, col, num)) {
                grid[row][col] = num
                if (fillGrid(grid)) {
                    return true
                }
                grid[row][col] = 0
            }
        }
        return false
    }

    private fun findEmptyCell(grid: List<MutableList<Int>>): Pair<Int, Int>? {
        for (row in grid.indices) {
            for (col in grid[row].indices) {
                if (grid[row][col] == 0) {
                    return Pair(row, col)
                }
            }
        }
        return null
    }

    private fun isValidMove(grid: List<MutableList<Int>>, row: Int, col: Int, num: Int): Boolean {
        for (x in grid.indices) {
            if (grid[row][x] == num) {
                return false
            }
        }
        for (y in grid.indices) {
            if (grid[y][col] == num) {
                return false
            }
        }
        val startRow = row - row % 3
        val startCol = col - col % 3
        for (i in 0 until 3) {
            for (j in 0 until 3) {
                if (grid[i + startRow][j + startCol] == num) {
                    return false
                }
            }
        }
        return true
    }

    private fun pokeHoles(grid: List<MutableList<Int>>, holes: Int): Stack<Hint> {
        val removedValues = Stack<Hint>()
        val indices: MutableList<Int> = (0..80).shuffled().toMutableList()

        while (removedValues.size < holes) {
            val index = indices.removeFirst()
            val rowIndex: Int = index / 9
            val colIndex: Int = index % 9

            if (grid[rowIndex][colIndex] == 0) continue

            removedValues.push(Hint(index, grid[rowIndex][colIndex]))
            grid[rowIndex][colIndex] = 0

            val proposedGrid = grid.map { it }
            val solutions = solve(proposedGrid,0, 0)
            if (solutions != 1) {
                grid[rowIndex][colIndex] = removedValues.pop().number
            }
        }
        return removedValues
    }

    private fun solve(grid: List<MutableList<Int>>, row: Int, col: Int): Int {
        if (row == 9) return 1  // Base case: reached the end of the board

        var nextRow = row
        var nextCol = col + 1

        // Move to the next row if the current column is at the end of the row
        if (nextCol == 9) {
            nextRow++
            nextCol = 0
        }

        // Skip already filled cells
        if (grid[row][col] != 0) return solve(grid, nextRow, nextCol)

        var count = 0

        // Try filling the current cell with each valid number (1-9)
        for (num in 1..9) {
            if (isValidMove(grid, row, col, num)) {
                grid[row][col] = num
                count += solve(grid, nextRow, nextCol)
                grid[row][col] = 0  // Backtrack
            }
        }
        return count
    }
}