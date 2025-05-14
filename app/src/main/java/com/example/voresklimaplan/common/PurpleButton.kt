package com.example.voresklimaplan.common

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt

@Composable
fun PurpleButton(
        buttonTekst: String
) {
    val color = Color("D2BFFF".toColorInt()) //lilla farve fra figma prototypen

    Button(
        onClick = {},
            colors = ButtonDefaults.buttonColors(containerColor = color),
            modifier = Modifier.padding(10.dp)
    ) {
        Text(
            buttonTekst,
            color = Color.White,
            fontSize = 25.sp
        )
    }
}
