package com.example.voresklimaplan.ui.screens



import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.voresklimaplan.R
import com.example.voresklimaplan.common.PurpleButton // SPØRG ANE
import com.example.voresklimaplan.common.TextFontDynaPuff
import com.example.voresklimaplan.common.dynaPuffFont


//Nikoleta har været her
@Composable
fun BackgroundScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F7FF))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Title()
            BarometerImage()
            PurpleButton(
                buttonTekst = "Opdater",
                fontFamily = dynaPuffFont,
                fontSize = 25
                ) // SPØRG ANE

        }
    }
}

//Nikoleta har været her
@Composable
fun Title() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextFontDynaPuff(
            textInput = "Vores KlimaPlan",
            fontSizeInput = 28
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextFontDynaPuff(
            textInput = "Gør en forskel",
            fontSizeInput = 16
        )
    }
}


//Nikoleta har været her
@Composable
fun BarometerImage() {
    Image(
        painter = painterResource(id = R.drawable.barometergraph),
        contentDescription = "Barometer Graph",
        modifier = Modifier.fillMaxWidth(),
        contentScale = ContentScale.Fit
    )
}



@Preview(showBackground = true)
@Composable
fun BackgroundScreenPreview() {
    BackgroundScreen()
}

class LearningPage {

}
