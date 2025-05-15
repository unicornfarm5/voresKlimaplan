package com.example.voresklimaplan.common

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.example.voresklimaplan.ui.screens.pressStartFont

//Linea
@Composable
fun TextFontGaming(textInput: String, fontSizeInput: Int) {
    Text(
        text = textInput,
        color = Color.Black,
        fontSize = fontSizeInput.sp,
        fontFamily = pressStartFont
    )

}