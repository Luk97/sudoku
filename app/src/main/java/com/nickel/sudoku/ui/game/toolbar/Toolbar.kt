package com.nickel.sudoku.ui.game.toolbar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Redo
import androidx.compose.material.icons.automirrored.filled.Undo
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.nickel.sudoku.ui.game.GameScreenInteraction
import com.nickel.sudoku.ui.game.GameScreenState
import com.nickel.sudoku.ui.theme.SudokuTheme

@Composable
internal fun Toolbar(
    buttonAvailability: GameScreenState.ButtonAvailability,
    onInteraction: (GameScreenInteraction) -> Unit = {}
) {
    Column {
        Row {
            ToolbarDigit(digit = 1, onInteraction = onInteraction)
            ToolbarDigit(digit = 2, onInteraction = onInteraction)
            ToolbarDigit(digit = 3, onInteraction = onInteraction)
            ToolbarButton(
                icon = Icons.AutoMirrored.Filled.Undo,
                enabled = buttonAvailability.undoActionsAvailable,
            ) {
                onInteraction(GameScreenInteraction.UndoClicked)
            }
        }
        Row {
            ToolbarDigit(digit = 4, onInteraction = onInteraction)
            ToolbarDigit(digit = 5, onInteraction = onInteraction)
            ToolbarDigit(digit = 6, onInteraction = onInteraction)
            ToolbarButton(
                icon = Icons.AutoMirrored.Filled.Redo,
                enabled = buttonAvailability.redoActionsAvailable
            ) {
                onInteraction(GameScreenInteraction.RedoClicked)
            }
        }
        Row {
            ToolbarDigit(digit = 7, onInteraction = onInteraction)
            ToolbarDigit(digit = 8, onInteraction = onInteraction)
            ToolbarDigit(digit = 9, onInteraction = onInteraction)
            ToolbarButton(
                icon = Icons.Default.Delete,
                enabled = buttonAvailability.removeDigitsAvailable
            ) {
                onInteraction(GameScreenInteraction.RemoveClicked)
            }
        }
    }
}

@Composable
private fun ToolbarDigit(
    digit: Int,
    onInteraction: (GameScreenInteraction) -> Unit = {}
) {
    IconButton(
        onClick = { onInteraction(GameScreenInteraction.DigitClicked(digit)) }
    ) {
        Text(
            text = "$digit",
            fontSize = 24.sp
        )
    }
}

@Preview
@Composable
private fun ToolbarPreview() {
    SudokuTheme {
        Toolbar(buttonAvailability = GameScreenState.ButtonAvailability())
    }
}