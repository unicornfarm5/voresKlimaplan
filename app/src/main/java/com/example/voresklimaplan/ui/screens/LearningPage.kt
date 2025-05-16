package com.example.voresklimaplan.ui.screens

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
import com.example.voresklimaplan.common.CustomDivider
import com.example.voresklimaplan.common.PurpleButton
import com.example.voresklimaplan.common.TextFontDynaPuff
import com.example.voresklimaplan.common.dynaPuffFont
import com.example.voresklimaplan.data.StepButtonData
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue


//Nikoleta har været her
@Composable
fun BackgroundScreen(navController: NavController) {
    val scrollState = rememberScrollState()

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
                fontSize = 25
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


//Nikoleta har været her
@Composable
fun GameThumbnail(navController: NavController, modifier: Modifier = Modifier) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextFontDynaPuff(
            textInput = "Spil",
            fontSizeInput = 20
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
            description = "   Gå eller cykl i skole – det forurener ikke og giver motion!\n" +
                    "\n" +
                    "    Tag bus eller tog i stedet for at køre i bil eller flyve.\n" +
                    "\n" +
                    "   Pas på naturen: Mindre transport betyder renere luft og glade dyr."
        ),
        StepButtonData(
            "Tænk grønt, også når du slapper af",
            Color(0xFFBBA6F2),
            description = "Hold ferie i Danmark og udforsk naturen.\n" +
                    "\n" +
                    "Gør noget: Hver rejse du vælger grønt, tæller for klimaet."
        ),
        StepButtonData(
            "Brug mindre og grønnere",
            Color(0xFF8FD8DF),
            description = "Brug det tøj du har – og pas godt på det.\n" +
                    "\n" +
                    "Byt eller køb genbrug i stedet for nyt.\n" +
                    "\n" +
                    "Smid mindre ud: Genbrug er guld for klimaet!"
        ),
        StepButtonData(
            "Spis Klimavenlig",
            Color(0xFFF29B9B),
            textColor = Color(0xFF8A1F1F),
            description = "\n" +
                    "Spis mere frugt og grønt – det er bedre for klimaet end kød.\n" +
                    "\n" +
                    "Vælg økologisk og lokalt, hvis du kan.\n" +
                    "\n" +
                    "Brug dine penge klogt: Støt mad, der er god for jorden."
        ),
        StepButtonData(
            "Brug dine ting i længere tid",
            Color(0xFF9BE59B),
            description = "\n" +
                    "Spis mere frugt og grønt – det er bedre for klimaet end kød.\n" +
                    "\n" +
                    "Vælg økologisk og lokalt, hvis du kan.\n" +
                    "\n" +
                    "Brug dine penge klogt: Støt mad, der er god for jorden."
        ),
        StepButtonData(
            "Gør dit hjem grønnere",
            Color(0xFFFFC44D),
            textColor = Color(0xFFB36A00),
            description = "Sluk for lys og skærme, når du ikke bruger dem.\n" +
                    "\n" +
                    "Brug mindre strøm – det hjælper både klimaet og elregningen.\n" +
                    "\n" +
                    "Hjælp din familie: Vis dem hvordan I kan spare energi sammen."
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
            fontSizeInput = 20
        )

        Spacer(modifier = Modifier.height(8.dp))

        buttons.forEach { button ->
            StyledStepButton(
                text = button.title,
                borderColor = button.borderColor,
                textColor = button.textColor,
                description = button.description
            )
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}


//Nikoleta har været her
@Composable
fun StyledStepButton(
    text: String,
    borderColor: Color,
    textColor: Color = Color.Black,
    description: String = ""
) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .border(2.dp, borderColor, RoundedCornerShape(12.dp))
            .padding(12.dp)
            .clickable { expanded = !expanded }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            TextFontDynaPuff(
                textInput = text,
                fontSizeInput = 18,
                color = textColor,
                modifier = Modifier.weight(1f)
            )
            TextFontDynaPuff(
                textInput = if (expanded) "▲" else "▼",
                fontSizeInput = 18,
                color = textColor,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        if (expanded) {
            Spacer(modifier = Modifier.height(8.dp))
            TextFontDynaPuff(
                textInput = description,
                fontSizeInput = 14,
                color = textColor
            )
        }
    }
}



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewBackgroundScreen() {
    val navController = rememberNavController()
    BackgroundScreen(navController = navController)
}
