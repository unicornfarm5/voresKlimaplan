package com.example.voresklimaplan.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposableTarget
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.voresklimaplan.R
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight

//fonts
public val rubrikBubblesFont = FontFamily(Font(R.font.rubikbubbles))
public val pressStartFont  = FontFamily(Font(R.font.pressstart))

@Composable
fun KlassensBy() {
    Box(
        modifier = Modifier
            .fillMaxSize()
    )
    {
        //baggrundsbillede
        Image(painter = painterResource(R.drawable.background_start_game),
            contentDescription = null,
            modifier = Modifier.fillMaxSize())
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    KlassensBy()
}
