package com.example.voresklimaplan.common

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

//feks sådan her men med font types
//overskrifter+tekst
@Composable
fun TextOnPage(textInput: String, fontSizeInput: Int) {
    Text(
        text = textInput,
        color = Color.Black,
        fontSize = fontSizeInput.sp,
        fontWeight = FontWeight.Bold
    )
}
