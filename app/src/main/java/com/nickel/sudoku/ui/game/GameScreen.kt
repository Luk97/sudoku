package com.nickel.sudoku.ui.game

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nickel.sudoku.data.Cell
import com.nickel.sudoku.data.FieldType
import com.nickel.sudoku.ui.game.toolbar.Toolbar
import com.nickel.sudoku.ui.theme.SudokuTheme
import com.nickel.sudoku.utils.TimeFormatter

@Composable
fun GameScreen(
    viewModel: GameScreenViewModel = hiltViewModel()
) {
    val gameScreenState by viewModel.state.collectAsState()

    GameScreen(
        gameScreenState = gameScreenState,
        onInteraction = viewModel::onInteraction
    )
}

@Composable
private fun GameScreen(
    gameScreenState: GameScreenState,
    onInteraction: (GameScreenInteraction) -> Unit = {}
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = TimeFormatter.format(gameScreenState.timer))
        SudokuBoard(
            selectedCellIndex = gameScreenState.selectedCellIndex,
            grid = gameScreenState.grid,
            onInteraction = onInteraction
        )
        Spacer(modifier = Modifier.height(16.dp))
        Toolbar(
            buttonAvailability = gameScreenState.buttonAvailability,
            onInteraction = onInteraction
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun GameScreenPreview() {
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
        GameScreen(
            gameScreenState = GameScreenState(
                grid = grid
            )
        )
    }
}