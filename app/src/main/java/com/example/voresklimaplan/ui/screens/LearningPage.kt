package com.example.voresklimaplan.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.voresklimaplan.R
import com.example.voresklimaplan.ui.common.CustomDivider
import com.example.voresklimaplan.ui.common.PurpleButton
import com.example.voresklimaplan.ui.common.TextFontDynaPuff
import com.example.voresklimaplan.ui.common.dynaPuffFont
import com.example.voresklimaplan.ui.common.StepButtonData
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext


//Nikoleta har været her
@Composable
fun BackgroundScreen(navController: NavController) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F7FF))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Title()
            BarometerImage()
            PurpleButton(
                buttonTekst = "Opdater",
                fontFamily = dynaPuffFont,
                fontSize = 25,
                navController = navController,
                onClick = {
                    //Linea
                    // todo: ide fra chatGPT
                    Intent(Intent.ACTION_VIEW, Uri.parse("https://skole.voresklimaplan.dk/")).apply {
                        context.startActivity(this)
                    }
                }
            )

            CustomDivider()

            Spacer(modifier = Modifier.height(16.dp))

            GameThumbnail(navController = navController)

            ClimaSteps()
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
            fontSizeInput = 28,
            modifier = Modifier
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextFontDynaPuff(
            textInput = "Gør en forskel",
            fontSizeInput = 16,
            modifier = Modifier
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


//Nikoleta har været her
@Composable
fun GameThumbnail(navController: NavController, modifier: Modifier = Modifier) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextFontDynaPuff(
            textInput = "Spil",
            fontSizeInput = 20,
            modifier = Modifier
        )

        Spacer(modifier = Modifier.height(8.dp))
    }

    Box(
        modifier = modifier
            .width(250.dp)
            .height(140.dp)
            .clip(RoundedCornerShape(12.dp))
            .border(2.dp, Color.Black, RoundedCornerShape(12.dp))
            .clickable {
                navController.navigate("GameLandingPage")
            }
    ) {
        Image(
            painter = painterResource(id = R.drawable.learningpagegameintro),
            contentDescription = "Game Intro Thumbnail",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}

//Nikoleta har været her
@Composable
fun ClimaSteps(modifier: Modifier = Modifier) {
    val buttons = listOf(
        StepButtonData(
            "Rejse mindre og grønnere",
            Color(0xFF9BE59B),
            description = "   Gå eller cykl i skole – det forurener ikke og giver motion!\n\n    Tag bus eller tog i stedet for at køre i bil eller flyve.\n\n   Pas på naturen: Mindre transport betyder renere luft og glade dyr."
        ),
        StepButtonData(
            "Tænk grønt, også når du slapper af",
            Color(0xFFBBA6F2),
            description = "Hold ferie i Danmark og udforsk naturen.\n\nGør noget: Hver rejse du vælger grønt, tæller for klimaet."
        ),
        StepButtonData(
            "Brug mindre og grønnere",
            Color(0xFF8FD8DF),
            description = "Brug det tøj du har – og pas godt på det.\n\nByt eller køb genbrug i stedet for nyt.\n\nSmid mindre ud: Genbrug er guld for klimaet!"
        ),
        StepButtonData(
            "Spis Klimavenligt",
            Color(0xFFF29B9B),
            textColor = Color(0xFF8A1F1F),
            description = "\nSpis mere frugt og grønt – det er bedre for klimaet end kød.\n\nVælg økologisk og lokalt, hvis du kan.\n\nBrug dine penge klogt: Støt mad, der er god for jorden."
        ),
        StepButtonData(
            "Brug dine ting i længere tid",
            Color(0xFF9BE59B),
            description = "\nSpis mere frugt og grønt – det er bedre for klimaet end kød.\n\nVælg økologisk og lokalt, hvis du kan.\n\nBrug dine penge klogt: Støt mad, der er god for jorden."
        ),
        StepButtonData(
            "Gør dit hjem grønnere",
            Color(0xFFFFC44D),
            textColor = Color(0xFFB36A00),
            description = "Sluk for lys og skærme, når du ikke bruger dem.\n\nBrug mindre strøm – det hjælper både klimaet og elregningen.\n\nHjælp din familie: Vis dem hvordan I kan spare energi sammen."
        )
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextFontDynaPuff(
            textInput = "KLIMAVENLIGE STEPS DER GØR EN FORSKEL",
            fontSizeInput = 20,
            modifier = Modifier
        )

        Spacer(modifier = Modifier.height(8.dp))

        buttons.forEach { button ->
            StyledStepButton(stepData = button)
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}


//Nikoleta har været her
@Composable
fun StyledStepButton(
stepData: StepButtonData
) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .border(2.dp, stepData.borderColor, RoundedCornerShape(12.dp))
            .padding(12.dp)
            .clickable { expanded = !expanded } // todo: Chatgpt her
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            TextFontDynaPuff(
                textInput = stepData.title,
                fontSizeInput = 18,
                color = stepData.textColor,
                modifier = Modifier.weight(1f)
            )
            TextFontDynaPuff(
                textInput = if (expanded) "▲" else "▼",
                fontSizeInput = 18,
                color = stepData.textColor,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        if (expanded) {
            Spacer(modifier = Modifier.height(8.dp))
            TextFontDynaPuff(
                textInput = stepData.description,
                fontSizeInput = 14,
                color = stepData.textColor,
                modifier = Modifier
            )
        }
    }
}