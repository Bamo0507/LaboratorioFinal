package com.bryan.laboratoriofinal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.bryan.laboratoriofinal.presentation.AssetNavHost
import com.bryan.laboratoriofinal.presentation.ui.theme.LaboratoriofinalTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LaboratoriofinalTheme {
                Scaffold(
                    modifier = Modifier,
                    content = { innerPadding ->
                        AssetNavHost(modifier = Modifier.padding(innerPadding))
                    }
                )
            }
        }
    }
}
