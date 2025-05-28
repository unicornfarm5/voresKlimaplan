package com.example.voresklimaplan.ui.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.navigation.NavController

@Composable
//Linea, Jonas og Føen
fun PurpleButton(
    navController: NavController,
    navigateTo: String? = null,//optional parameter
    buttonTekst: String,
    fontFamily: FontFamily,
    fontSize: Int,
    onClick: (() -> Unit)? = null,//optional parameter
) {
    val color = Color("#D2BFFF".toColorInt()) //lilla farve fra figma prototypen

    Button(
        onClick = {
            //knappen kan navigere men kan også bruges uden navigation
            if (navigateTo != null ) {
                navController.navigate(navigateTo)
            }
            if (onClick != null) {
                onClick()
            }
        },
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
