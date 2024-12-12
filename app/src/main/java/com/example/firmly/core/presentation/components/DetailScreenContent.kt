package com.example.firmly.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.firmly.core.domain.contractor.ContractorDetail
import java.sql.Date
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun DetailScreenContent(
    contractorDetail: ContractorDetail,
    modifier: Modifier = Modifier,
) {
    val dateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
    val formattedDate = dateFormat.format(Date(contractorDetail.creationDate))

    Column(
        modifier = modifier,
    ) {
        Text(
            text = "Dane podstawowe",
            fontSize = 20.sp,
        )
        HorizontalDivider(
            modifier = Modifier
                .padding(top = 5.dp, bottom = 10.dp)
                .background(MaterialTheme.colorScheme.onPrimary)
                .fillMaxWidth(.3f),
            thickness = 1.dp
        )
        TextWithTitle(
            fieldName = "NAZWA",
            fieldText = contractorDetail.name
        )
        Row {
            TextWithTitle(
                fieldName = "IMIE",
                fieldText = contractorDetail.firstName?.ifEmpty { "-" } ?: "-",
                occupyWidth = .5f,
                descriptionFontSize = 12.sp,
            )
            TextWithTitle(
                fieldName = "NAZWISKO",
                fieldText = contractorDetail.lastName?.ifEmpty { "-" } ?: "-",
            )
        }
        Row {
            TextWithTitle(
                fieldName = "NIP",
                fieldText = contractorDetail.taxNumber.ifEmpty { "-" },
                occupyWidth = .5f,
                descriptionFontSize = 12.sp,
            )
            TextWithTitle(
                fieldName = "REGON",
                fieldText = contractorDetail.buisnessRegistryNumber.ifEmpty { "-" },
            )
        }

        Spacer(modifier = Modifier.padding(top = 20.dp))

        Text(
            text = "Dane adresowe",
            fontSize = 20.sp,
        )
        HorizontalDivider(
            modifier = Modifier
                .padding(top = 5.dp, bottom = 10.dp)
                .background(MaterialTheme.colorScheme.onPrimary)
                .fillMaxWidth(.3f),
            thickness = 1.dp
        )

        Row {
            TextWithTitle(
                fieldName = "ULICA",
                fieldText = contractorDetail.street?.ifEmpty { "-" } ?: "-",
                occupyWidth = .5f,
                descriptionFontSize = 12.sp,
            )
            TextWithTitle(
                fieldName = "NR DOMU" +
                        if (contractorDetail.apartment.isNullOrEmpty()) {
                            ""
                        } else {
                            " / NR LOKALU"
                        },
                fieldText = "${contractorDetail.building?.ifEmpty { "-" } ?: "-"} " +
                        if (contractorDetail.apartment.isNullOrEmpty()) {
                            ""
                        } else {
                            " / ${contractorDetail.apartment}"
                        },
            )
        }

        Row {
            TextWithTitle(
                fieldName = "KOD POCZTOWY",
                fieldText = contractorDetail.postalCode?.ifEmpty { "-" } ?: "-",
                occupyWidth = .5f,
                descriptionFontSize = 12.sp,
            )
            TextWithTitle(
                fieldName = "MIEJSCOWOŚĆ",
                fieldText = contractorDetail.city?.ifEmpty { "-" } ?: "-",
            )
        }

        Row {
            TextWithTitle(
                fieldName = "GMINA",
                fieldText = contractorDetail.municipality?.ifEmpty { "-" } ?: "-",
                occupyWidth = .5f,
                descriptionFontSize = 12.sp,
            )
            TextWithTitle(
                fieldName = "POWIAT",
                fieldText = contractorDetail.county?.ifEmpty { "-" } ?: "-",
            )
        }

        Row {
            TextWithTitle(
                fieldName = "WOJEWÓDZTWO",
                fieldText = contractorDetail.province?.ifEmpty { "-" } ?: "-",
                occupyWidth = .5f,
                descriptionFontSize = 12.sp,
            )
            TextWithTitle(
                fieldName = "KRAJ",
                fieldText = contractorDetail.country?.ifEmpty { "-" } ?: "-",
            )
        }


        Spacer(modifier = Modifier.padding(top = 20.dp))

        Text(
            text = "Dane kontaktowe",
            fontSize = 20.sp,
        )
        HorizontalDivider(
            modifier = Modifier
                .padding(top = 5.dp, bottom = 10.dp)
                .background(MaterialTheme.colorScheme.onPrimary)
                .fillMaxWidth(.3f),
            thickness = 1.dp
        )

        Row {
            TextWithTitle(
                fieldName = "TELEFON",
                fieldText = contractorDetail.phone?.ifEmpty { "-" } ?: "-",
                occupyWidth = .5f,
                descriptionFontSize = 12.sp,
            )
            TextWithTitle(
                fieldName = "E-MAIL",
                fieldText = "-",
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        if (contractorDetail.creationDate != 0L) {
            Row {
                Text(
                    text = "Ostatnia aktualizacja danych: $formattedDate.",
                    fontSize = 12.sp,
                )
            }
        }
    }
}
