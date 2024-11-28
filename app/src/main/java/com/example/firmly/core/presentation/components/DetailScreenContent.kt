package com.example.firmly.core.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.firmly.core.domain.contractor.ContractorDetail

@Composable
fun DetailScreenContent(
    contractorDetail: ContractorDetail,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        Text(
            text = "Dane podstawowe"
        )
        TextWithTitle(
            fieldName = "Nazwa",
            fieldText = contractorDetail.name
        )
        Row {
            TextWithTitle(
                fieldName = "Imie",
                fieldText = contractorDetail.firstName?.ifEmpty { "-" } ?: "-"
            )
            TextWithTitle(
                fieldName = "Nazwisko",
                fieldText = contractorDetail.lastName?.ifEmpty { "-" } ?: "-"
            )
        }
    }
}
