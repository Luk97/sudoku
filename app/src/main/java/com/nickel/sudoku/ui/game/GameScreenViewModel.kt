package com.nickel.sudoku.ui.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nickel.sudoku.data.Cell
import com.nickel.sudoku.model.SudokuAnalyzer
import com.nickel.sudoku.model.SudokuGenerator
import com.nickel.sudoku.undoredo.UndoRedoAction
import com.nickel.sudoku.undoredo.UndoRedoManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameScreenViewModel @Inject constructor(
    generator: SudokuGenerator,
    private val analyzer: SudokuAnalyzer,
    private val undoRedoManager: UndoRedoManager
) : ViewModel() {

    private val _state = MutableStateFlow(GameScreenState())
    internal val state = _state.asStateFlow()

    private var timerJob: Job? = null

    init {
        val sudoku = generator.generateSudoku()
        _state.update {
            it.copy(
                grid = sudoku.grid,
                solution = sudoku.solution
            )
        }
        startTimer()
    }

    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
    }

    internal fun onInteraction(interaction: GameScreenInteraction) {
        when (interaction) {
            is GameScreenInteraction.CellClicked -> onCellClicked(interaction.index)
            is GameScreenInteraction.DigitClicked -> onDigitClicked(interaction.digit)
            GameScreenInteraction.UndoClicked -> onUndoClicked()
            GameScreenInteraction.RedoClicked -> onRedoClicked()
            GameScreenInteraction.RemoveClicked -> onRemoveClicked()
        }
    }

    private fun onCellClicked(index: Int) {
        _state.update {
            it.copy(
                selectedCellIndex = index,
                buttonAvailability = it.buttonAvailability.copy(
                    removeDigitsAvailable = analyzer.isFieldEntryRemovable(it.grid, index)
                )
            )
        }
    }

    private fun onDigitClicked(digit: Int) {
        val grid = _state.value.grid
        val index = _state.value.selectedCellIndex
        val prevDigit = analyzer.getFieldEntry(grid, index)

        if (analyzer.isFieldEntryMutable(grid, index)) {
            undoRedoManager.addUndoAction(UndoRedoAction.DigitChanged(index, digit, prevDigit))
            updateDigit(index, digit)
            updateButtonAvailability()
        }
    }

    private fun onUndoClicked() {
        when (val action = undoRedoManager.undoAction()) {
            is UndoRedoAction.DigitChanged -> updateDigit(action.index, action.prevDigit)
            else -> {}
        }
        updateButtonAvailability()
    }

    private fun onRedoClicked() {
        when (val action = undoRedoManager.redoAction()) {
            is UndoRedoAction.DigitChanged -> updateDigit(action.index, action.digit)
            else -> {}
        }
        updateButtonAvailability()
    }

    private fun onRemoveClicked() {
        val state = _state.value
        val prevDigit = analyzer.getFieldEntry(state.grid, state.selectedCellIndex)

        undoRedoManager.addUndoAction(UndoRedoAction.DigitChanged(state.selectedCellIndex, 0, prevDigit))
        updateDigit(state.selectedCellIndex, 0)
        updateButtonAvailability()
    }

    private fun updateDigit(index: Int, digit: Int) {
        _state.update {
            it.copy(
                grid = analyzer.updateSudokuGrid(
                    grid = it.grid,
                    index = index,
                    entry = digit,
                    solution = it.solution
                ),
                selectedCellIndex = index
            )
        }
    }

    private fun updateButtonAvailability() {
        _state.update {
            it.copy(
                buttonAvailability = it.buttonAvailability.copy(
                    undoActionsAvailable = undoRedoManager.undoActionsAvailable(),
                    redoActionsAvailable = undoRedoManager.redoActionsAvailable(),
                    removeDigitsAvailable = analyzer.isFieldEntryRemovable(it.grid, it.selectedCellIndex)
                )
            )
        }
    }

    private fun startTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (true) {
                delay(1000L)
                _state.update {
                    it.copy(timer = it.timer + 1)
                }
            }
        }
    }
}

internal data class GameScreenState(
    val grid: List<Cell> = emptyList(),
    val solution: List<Int> = emptyList(),
    val selectedCellIndex: Int = 0,
    val timer: Long = 0L,
    val buttonAvailability: ButtonAvailability = ButtonAvailability()
) {
    data class ButtonAvailability(
        val undoActionsAvailable: Boolean = false,
        val redoActionsAvailable: Boolean = false,
        val removeDigitsAvailable: Boolean = false
    )
}

internal sealed class GameScreenInteraction {
    data class CellClicked(val index: Int) : GameScreenInteraction()
    data class DigitClicked(val digit: Int) : GameScreenInteraction()
    data object UndoClicked : GameScreenInteraction()
    data object RedoClicked: GameScreenInteraction()
    data object RemoveClicked: GameScreenInteraction()
}