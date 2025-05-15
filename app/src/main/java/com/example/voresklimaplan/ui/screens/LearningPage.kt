package com.example.voresklimaplan.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.voresklimaplan.R
import com.example.voresklimaplan.common.PurpleButton // SPØRG ANE


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
            Spacer(modifier = Modifier.height(16.dp))
            PurpleButton(buttonTekst = "Opdater") // SPØRG ANE
        }
    }
}

//Nikoleta har været her
@Composable
fun Title() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Vores KlimaPlan",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF311B92)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Gør en forskel",
            fontSize = 16.sp,
            color = Color(0xFF311B92)
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
