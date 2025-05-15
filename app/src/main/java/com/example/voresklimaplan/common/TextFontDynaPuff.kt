package com.example.voresklimaplan.common

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import com.example.voresklimaplan.R

//Nikoleta var her

val dynaPuffFont = FontFamily(Font(R.font.dynapuff))

@Composable
fun TextFontDynaPuff(textInput: String, fontSizeInput: Int) {
        val color = Color("#191749".toColorInt())

        Text(
            text = textInput,
            color = color,
            fontSize = fontSizeInput.sp,
            fontFamily = dynaPuffFont
        )

    }
