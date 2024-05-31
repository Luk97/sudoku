package com.nickel.sudoku.ui.game.toolbar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.nickel.sudoku.ui.theme.SudokuTheme

@Composable
fun ToolbarButton(
    icon: ImageVector,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit = {}
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        shape = ButtonDefaults.filledTonalShape
    ) {
        Icon(imageVector = icon, contentDescription = null)
    }
}

@Preview(showSystemUi = true)
@Composable
private fun ToolbarButtonPreview() {
    SudokuTheme {
        ToolbarButton(icon = Icons.Default.Image)
    }
}