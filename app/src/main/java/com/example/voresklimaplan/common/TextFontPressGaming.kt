package com.example.voresklimaplan.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.voresklimaplan.R

//Linea
public val pressStartFont  = FontFamily(Font(R.font.pressstart))

@Composable
fun TextFontGaming(
        textInput: String,
        fontSizeInput: Int,
        align: TextAlign = TextAlign.Left //den er til venstre, medmindre du kaldere den med center
        //like this:  align = TextAlign.Center
) {
    Text(
        text = textInput,
        color = Color.Black,
        fontSize = fontSizeInput.sp,
        fontFamily = pressStartFont,
        lineHeight = (fontSizeInput + 10).sp,
        modifier = Modifier.fillMaxWidth(), //for at kunne bruge align-center
        textAlign = align
    )

}