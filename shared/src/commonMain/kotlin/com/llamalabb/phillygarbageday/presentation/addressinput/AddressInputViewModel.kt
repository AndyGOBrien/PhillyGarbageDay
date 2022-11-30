package com.llamalabb.phillygarbageday.presentation.addressinput

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.llamalabb.phillygarbageday.presentation.addressinput.AddressInputScreenAction.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AddressInputViewModel : ViewModel() {

    private val _state = MutableStateFlow(AddressInputScreenState())
    val state = _state.asStateFlow()

    private fun reduce(action: BaseAction, state: AddressInputScreenState) {
        _state.value = state.copy(
            isLoading = reduceIsLoading(action, state),
            addressInput = reduceAddressInput(action, state),
            addresses = reduceAddresses(action, state),
        )
    }

    private fun reduceAddressInput(
        action: BaseAction,
        state: AddressInputScreenState
    ): String = when(action) {
        is UserInputAddressText -> action.text
        is UserTappedAddressItem -> "${action.addressItem.streetAddress}, ${action.addressItem.zipCode}"
        is UserTappedClearContent -> ""
        else -> state.addressInput
    }

    private fun reduceIsLoading(
        action: BaseAction,
        state: AddressInputScreenState
    ): Boolean = when(action) {
        is UserTappedLoadData -> true
        is DataLoadSuccess -> false
        else -> state.isLoading
    }

    private fun reduceAddresses(
        action: BaseAction,
        state: AddressInputScreenState
    ): List<AddressItem> = when(action) {
        is DataLoadSuccess -> action.addressList
        is UserTappedClearContent -> emptyList()
        else -> state.addresses
    }

    private fun loadDataSideEffect() {
        viewModelScope.launch {
            delay(3000)
            val result = createMockAddressList()
            dispatch(DataLoadSuccess(result))
        }
    }

    fun dispatch(action: BaseAction) {
        reduce(action, _state.value)
        when(action) {
            is UserTappedLoadData -> loadDataSideEffect()
            else -> Unit
        }
    }

    private fun createMockAddressList() = (0..9).map {
        AddressItem("$it Random St", "$it$it$it$it$it", "$it km")
    }
}

sealed class AddressInputScreenAction : BaseAction {
    object UserTappedLoadData : AddressInputScreenAction()
    object UserTappedClearContent : AddressInputScreenAction()
    class UserTappedAddressItem(val addressItem: AddressItem) : AddressInputScreenAction()
    class UserInputAddressText(val text: String) : AddressInputScreenAction()
    class DataLoadSuccess(val addressList: List<AddressItem>) : AddressInputScreenAction()
}

interface BaseAction