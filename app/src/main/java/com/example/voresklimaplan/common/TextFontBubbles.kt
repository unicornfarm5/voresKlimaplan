package com.example.voresklimaplan.common

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import com.example.voresklimaplan.R

//Linea
public val rubrikBubblesFont = FontFamily(Font(R.font.rubikbubbles))

@Composable
fun TextFontBubbles(textInput: String, fontSizeInput: Int) {
    val color = Color("#1F5229".toColorInt()) //grøn farve fra figma prototypen

    Text(
        text = textInput,
        color = color,
        fontSize = fontSizeInput.sp,
        fontFamily = rubrikBubblesFont
    )
}
