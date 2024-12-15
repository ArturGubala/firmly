package com.example.firmly.core.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.firmly.core.domain.contractor.ContractorListItem

@Composable
fun ContractorCard(
    contractor: ContractorListItem,
    modifier: Modifier,
    onCardClick: () -> Unit = {}
) {
    ElevatedCard(
        modifier = modifier,
        onClick = { onCardClick() }
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 7.dp, vertical = 5.dp)
        ) {
            TextWithTitle(
                fieldName = "Nazwa",
                fieldText = contractor.name,
                bottomSpace = 10.dp
            )
            Row(
                Modifier.fillMaxWidth()
            ) {
                TextWithTitle(
                    fieldName = "NIP",
                    fieldText = contractor.taxNumber,
                    bottomSpace = 0.dp,
                    occupyWidth = .4f
                )
                TextWithTitle(
                    fieldName = "Regon",
                    fieldText = contractor.buisnessRegistryNumber,
                    bottomSpace = 0.dp,
                )
            }
            Row(
                Modifier.fillMaxWidth()
            ) {
                TextWithTitle(
                    fieldName = "Kod pocztowy",
                    fieldText = contractor.postalCode ?: "",
                    occupyWidth = .4f
                )
                TextWithTitle(
                    fieldName = "Miasto",
                    fieldText = contractor.city ?: ""
                )
            }
        }
    }
}
