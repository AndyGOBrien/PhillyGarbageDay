package com.llamalabb.phillygarbageday.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.llamalabb.phillygarbageday.android.ui.NavGraphs
import com.llamalabb.phillygarbageday.android.ui.theme.TrashTheme
import com.ramcosta.composedestinations.DestinationsNavHost


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TrashTheme {
               DestinationsNavHost(navGraph = NavGraphs.root)
            }
        }


    }
}

