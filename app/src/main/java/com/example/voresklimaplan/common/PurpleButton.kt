package com.example.voresklimaplan.common

import androidx.annotation.Size
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import com.example.voresklimaplan.R

@Composable
//Linea (og Føen sikkert)
fun PurpleButton(
        buttonTekst: String,
        fontFamily: FontFamily,
        fontSize: Int
) {
    val color = Color("#D2BFFF".toColorInt()) //lilla farve fra figma prototypen

    Button(
        onClick = {},
            colors = ButtonDefaults.buttonColors(containerColor = color),
            border = BorderStroke(2.dp, Color.Black),
            modifier = Modifier
                .padding(20.dp)
    ) {
        Text(
            text = buttonTekst,
            color = Color.Black,
            fontSize = fontSize.sp,
            fontFamily = fontFamily,
        )
    }
}
