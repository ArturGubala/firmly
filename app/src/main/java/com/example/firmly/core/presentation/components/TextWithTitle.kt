package com.example.firmly.core.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.firmly.ui.theme.FirmlyTheme

@Composable
fun TextWithTitle(
    fieldName: String,
    fieldText: String,
    bottomSpace: Dp = 2.dp,
    occupyWidth: Float = 1f,
    descriptionFontSize: TextUnit = 11.sp,
    textFontSize: TextUnit = 18.sp
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(occupyWidth)
            .padding(bottom = bottomSpace),
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = fieldName,
            fontSize = descriptionFontSize,
        )
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = fieldText.ifEmpty { "-" },
            fontSize = textFontSize,
        )
    }
}

@PreviewLightDark
@Composable
private fun TextWithTitlePreview() {
    FirmlyTheme {
        TextWithTitle(
            fieldName = "Nazwa",
            fieldText = "Firmowy firm"
        )
    }
}
