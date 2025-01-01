package com.example.firmly.settings

import android.widget.Toast
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.chargemap.compose.numberpicker.*
import com.example.firmly.core.presentation.components.FirmlyTopAppBar
import com.example.firmly.core.presentation.navigation.TopLevelDestination
import com.example.firmly.core.presentation.util.ObserveAsEvents
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SettingsRoute(
    onBackClick: () -> Unit,
    viewModel: SettingsViewModel = koinViewModel()
) {

    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is SettingsEvent.Success -> {
                Toast.makeText(
                    context,
                    event.message,
                    Toast.LENGTH_LONG
                ).show()
            }
        }}

    SettingsScreen(
        onBackClick = onBackClick,
        state = state,
        onAction = viewModel::onAction
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SettingsScreen(
    onBackClick: () -> Unit,
    state: SettingsState,
    onAction: (SettingsAction) -> Unit
) {

    Scaffold(
        topBar = {
            FirmlyTopAppBar(
                titleRes = TopLevelDestination.SETTINGS.titleTextId,
                navigationIcon = Icons.AutoMirrored.Filled.ArrowBack,
                navigationIconContentDescription = "Navigation icon",
                onNavigationClick = onBackClick,
            )
        },
        content = { padding ->
            ElevatedCard(
                modifier = Modifier
                    .padding(padding)
                    .padding(horizontal = 10.dp)
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .padding(all = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val possibleValues = listOf(5, 10, 15)

                    Text(
                        text = "Liczba ostatnio przeglądanych kontrahentów, którzy nie zostali zapisani",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.weight(.7f)
                            .padding(horizontal = 5.dp)
                    )
                    ListItemPicker(
                        modifier = Modifier.weight(.3f),
                        label = { it.toString() },
                        value = state.numberOfSavedTemporaryContractors,
                        onValueChange = {
                            onAction(SettingsAction.OnNumberOfSavedTemporaryContractorsChange(it))
                        },
                        list = possibleValues,
                        dividersColor = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    )
}
