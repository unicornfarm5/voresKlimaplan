package com.example.voresklimaplan.common

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import com.example.voresklimaplan.R

//Nikoleta var her

val dynaPuffFont = FontFamily(Font(R.font.dynapuff))

@Composable
fun TextFontDynaPuff(
    textInput: String,
    fontSizeInput: Int,
    color: Color = Color("#191749".toColorInt()),  // Default color, can be overridden
    modifier: Modifier = Modifier                  // Add this line
) {
    Text(
        text = textInput,
        fontSize = fontSizeInput.sp,
        color = color,
        fontFamily = dynaPuffFont,
        modifier = modifier                         // Use the parameter here
    )
}
