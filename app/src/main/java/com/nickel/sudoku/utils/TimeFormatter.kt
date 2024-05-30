package com.nickel.sudoku.utils

class TimeFormatter {
    companion object {
        fun format(time: Long): String {
            val hours = time / 3600
            val minutes = (time % 3600) / 60
            val remainingSeconds = time % 60
            return String.format("%02d:%02d:%02d", hours, minutes, remainingSeconds)
        }
    }
}