package com.example.voresklimaplan.common

import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

//Nikoleta har været her

@Composable
fun CustomDivider(
    color: Color = Color.LightGray,
    thickness: Dp = 2.dp,
    paddingHorizontal: Dp = 16.dp
) {
    HorizontalDivider(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = paddingHorizontal),
        thickness = thickness,
        color = color
    )
}