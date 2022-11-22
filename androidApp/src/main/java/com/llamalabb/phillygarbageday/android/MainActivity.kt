package com.llamalabb.phillygarbageday.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.llamalabb.phillygarbageday.android.ui.NavGraphs
import com.llamalabb.phillygarbageday.android.ui.theme.KMMNewsTheme
import com.ramcosta.composedestinations.DestinationsNavHost


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            KMMNewsTheme {
               DestinationsNavHost(navGraph = NavGraphs.root)
            }
        }


    }
}

