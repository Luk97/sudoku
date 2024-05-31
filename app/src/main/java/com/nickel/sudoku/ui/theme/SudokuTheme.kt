package com.nickel.sudoku.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf

@Composable
fun SudokuTheme(
    darkMode: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalSudokuColors provides if (darkMode) DarkColorScheme else LightColorScheme,
        LocalDarkModeEnabled provides darkMode,
        content = content
    )
}

object SudokuTheme {
    val colorScheme: SudokuColorScheme
        @Composable
        @ReadOnlyComposable
        get() = LocalSudokuColors.current
}

private val LocalSudokuColors = staticCompositionLocalOf<SudokuColorScheme> { LightColorScheme }
private val LocalDarkModeEnabled = staticCompositionLocalOf { false }