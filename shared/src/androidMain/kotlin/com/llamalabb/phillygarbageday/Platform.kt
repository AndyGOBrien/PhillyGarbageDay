package com.llamalabb.phillygarbageday


import android.os.Parcelable
import com.llamalabb.phillygarbageday.presentation.addressinput.AddressInputViewModel
import com.llamalabb.phillygarbageday.presentation.home.HomeScreenViewModel
import com.llamalabb.phillygarbageday.presentation.newdetails.NewsDetailsViewModel
import com.llamalabb.phillygarbageday.presentation.readlater.ReadLaterViewModel
import io.ktor.client.engine.android.*
import kotlinx.parcelize.Parcelize
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * shared implementation of parcelable
 */

actual typealias CommonParcelable = Parcelable


actual fun platformModule() = module {


    single { Android.create() }
    /**
     *
     * for android koin has a special viewmodel scope that we can use
     * to create a viewmodel
     *
     */

    viewModel { HomeScreenViewModel(get()) }

    viewModel { AddressInputViewModel(get()) }

    viewModel { NewsDetailsViewModel(get()) }

    viewModel { ReadLaterViewModel(get()) }

}
