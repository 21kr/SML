package com.mrp.sml

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.mrp.sml.ui.HelloScreen
import com.mrp.sml.ui.theme.SMLTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SMLTheme {
                HelloScreen()
            }
        }
    }
}
