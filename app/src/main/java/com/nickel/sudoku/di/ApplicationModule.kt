package com.nickel.sudoku.di

import com.nickel.sudoku.model.SudokuAnalyzer
import com.nickel.sudoku.model.SudokuGenerator
import com.nickel.sudoku.undoredo.UndoRedoManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.util.Stack
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Provides
    @Singleton
    fun provideSudokuGenerator() = SudokuGenerator()

    @Provides
    @Singleton
    fun provideSudokuAnalyzer() = SudokuAnalyzer()

    @Provides
    @Singleton
    fun provideUndoRedoManager() = UndoRedoManager(
        undoActions = Stack(),
        redoActions = Stack()
    )
}