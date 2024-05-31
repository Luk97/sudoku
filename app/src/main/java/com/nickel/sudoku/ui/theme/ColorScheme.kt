package com.nickel.sudoku.ui.theme

import androidx.compose.ui.graphics.Color

interface SudokuColorScheme {
    val primary: Color
    val onPrimary: Color
}

object LightColorScheme: SudokuColorScheme {
    override val primary: Color = UltraViolette
    override val onPrimary: Color = Black
}

object DarkColorScheme: SudokuColorScheme {
    override val primary: Color = UltraViolette
    override val onPrimary: Color = Black
}