package com.llamalabb.phillygarbageday


import android.os.Parcelable
import com.llamalabb.phillygarbageday.presentation.addressinput.AddressInputViewModel
import io.ktor.client.engine.android.*
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

    viewModel { AddressInputViewModel(get()) }

}
