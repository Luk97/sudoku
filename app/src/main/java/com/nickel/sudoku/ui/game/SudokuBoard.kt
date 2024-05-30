package com.nickel.sudoku.ui.game

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nickel.sudoku.data.Cell
import com.nickel.sudoku.data.FieldType
import com.nickel.sudoku.ui.theme.SudokuTheme
import kotlin.random.Random

private const val BOARD_PADDING = 16
private const val DIVIDER_THIN = 1
private const val DIVIDER_THICK = 3
private const val COLS = 9

@Composable
internal fun SudokuBoard(
    grid: List<Cell>,
    selectedCellIndex: Int,
    onInteraction: (GameScreenInteraction) -> Unit = {}
) {
    val dividerSize = DIVIDER_THICK.times(2) + DIVIDER_THIN.times(6)
    val cellSize = LocalConfiguration.current.screenWidthDp.minus(BOARD_PADDING).div(COLS)
    val boardSize = cellSize.times(COLS).plus(dividerSize)
    Column(
        modifier = Modifier
            .width(boardSize.dp)
            .border(2.dp, Color.Gray, RoundedCornerShape(8.dp))
            .clip(RoundedCornerShape(8.dp))
    ) {
        repeat(9) { rowIndex ->
            Row(
                modifier = Modifier.width(boardSize.dp)
            ) {
                repeat(9) { colIndex ->
                    val index = rowIndex * 9 + colIndex
                    SudokuCell(
                        cell = grid[index],
                        selected = index == selectedCellIndex,
                        modifier = Modifier
                            .size(cellSize.dp)
                            .clickable { onInteraction(GameScreenInteraction.CellClicked(index)) }
                    )
                    VerticalDivider(
                        thickness = if (colIndex == 2 || colIndex == 5) {
                            DIVIDER_THICK.dp
                        } else DIVIDER_THIN.dp,
                        modifier = Modifier.height(cellSize.dp)
                    )
                }
            }
            HorizontalDivider(
                thickness = if (rowIndex == 2 || rowIndex == 5) {
                    DIVIDER_THICK.dp
                } else DIVIDER_THIN.dp,
                modifier = Modifier.width(boardSize.dp)
            )
        }
    }
}

@Composable
fun SudokuCell(
    cell: Cell,
    selected: Boolean,
    modifier: Modifier = Modifier
) {
    val backgroundColor = if (selected) Color.LightGray else Color.White
    Box(
        modifier = modifier.background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        when(cell.fieldType) {
            FieldType.GIVEN -> Text(
                text = "${cell.entry}",
                color = Color.Black,
                fontSize = 24.sp
            )
            FieldType.ENTRY -> Text(
                text = "${cell.entry}",
                color = Color.Blue,
                fontSize = 24.sp
            )
            FieldType.ERROR -> Text(
                text = "${cell.entry}",
                color = Color.Red,
                fontSize = 24.sp
            )
           else -> {}
        }
    }
}

@Preview
@Composable
private fun SudokuBoardPreview() {
    val grid: MutableList<Cell> = mutableListOf()
    repeat(81) {
        grid.add(
            Cell(
                fieldType = FieldType.GIVEN,
                entry = (1..9).random()
            )
        )
    }
    SudokuTheme {
        SudokuBoard(
            grid = grid,
            selectedCellIndex = 0
        )
    }
}
