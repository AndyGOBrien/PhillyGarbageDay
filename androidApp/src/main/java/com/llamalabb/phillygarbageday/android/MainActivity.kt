package com.llamalabb.phillygarbageday.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import com.llamalabb.phillygarbageday.android.ui.NavGraphs
import com.llamalabb.phillygarbageday.android.ui.destinations.AddressInputScreenDestination
import com.llamalabb.phillygarbageday.android.ui.destinations.TrashDayScreenDestination
import com.llamalabb.phillygarbageday.android.ui.theme.TrashTheme
import com.llamalabb.phillygarbageday.data.local.service.AddressRealmService
import com.llamalabb.phillygarbageday.data.local.service.IAddressRealmService
import com.ramcosta.composedestinations.DestinationsNavHost
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.koin.android.ext.android.get


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val realmService = get<IAddressRealmService>()

        lifecycleScope.launch {
            val needAddress = realmService.getStreetAddress()?.let { false } ?: true
            setContent {
                TrashTheme {
                    val startRoute = if (needAddress) {
                        AddressInputScreenDestination
                    } else {
                        TrashDayScreenDestination
                    }
                    DestinationsNavHost(navGraph = NavGraphs.root, startRoute = startRoute)
                }
            }
        }
    }
}
