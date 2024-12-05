package com.example.firmly

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.firmly.ui.theme.FirmlyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge(
//            statusBarStyle = SystemBarStyle.dark(0x00000000),
            navigationBarStyle = SystemBarStyle.dark(0x00000000)
        )
        super.onCreate(savedInstanceState)

        setContent {
            val appState = rememberFirmlyAppState()

            FirmlyTheme {
                FirmlyApp(appState)
            }
        }
    }
}
