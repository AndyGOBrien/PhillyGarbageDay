package com.llamalabb.phillygarbageday.presentation.addressinput

import com.llamalabb.phillygarbageday.data.repository.ITrashDayRepository
import com.llamalabb.phillygarbageday.presentation.BaseAction
import com.llamalabb.phillygarbageday.presentation.BaseUiEvent
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.llamalabb.phillygarbageday.presentation.addressinput.AddressInputScreenAction.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class AddressInputViewModel(private val repo: ITrashDayRepository) : ViewModel() {

    private val _state = MutableStateFlow(AddressInputScreenState())
    val state = _state.asStateFlow()

    private val _uiEvent = MutableSharedFlow<BaseUiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    fun dispatch(action: BaseAction) {
        reduce(action, _state.value)
        launchSideEffects(action)
    }

    private fun dispatchUiEvent(uiEvent: BaseUiEvent) {
        viewModelScope.launch { _uiEvent.emit(uiEvent) }
    }

    /** Reducers */
    //region Reducers

    private fun reduce(action: BaseAction, state: AddressInputScreenState) {
        _state.value = state.copy(
            isLoading = reduceIsLoading(action, state),
            addressInput = reduceAddressInput(action, state),
            addressInputError = reduceAddressInputError(action, state),
        )
    }

    private fun reduceAddressInput(
        action: BaseAction,
        state: AddressInputScreenState
    ): String = when (action) {
        is UserInputAddressText -> action.text
        else -> state.addressInput
    }

    private fun reduceAddressInputError(
        action: BaseAction,
        state: AddressInputScreenState
    ): String? = when (action) {
        is AddressValidatedError -> "Please enter a valid address"
        is AddressValidatedSuccess -> null
        else -> state.addressInputError
    }

    private fun reduceIsLoading(
        action: BaseAction,
        state: AddressInputScreenState
    ): Boolean = when (action) {
        is UserSubmittedAddress -> true
        is AddressValidatedSuccess -> false
        is AddressValidatedError -> false
        else -> state.isLoading
    }

    //endregion

    /** Side Effects */
    //region Side Effects

    private fun launchSideEffects(action: BaseAction) {
        when (action) {
            is UserSubmittedAddress -> loadAddressDataSideEffect(action.address)
            is AddressValidatedSuccess -> dispatchUiEvent(Navigation.ShowTrashDay)
            else -> Unit
        }
    }

    private fun loadAddressDataSideEffect(address: String) {
        viewModelScope.launch {
            val isValidAddress = repo.validateAddress(address)
            dispatch(if (isValidAddress) AddressValidatedSuccess else AddressValidatedError)
        }
    }

    //endregion
}

sealed class Navigation : BaseUiEvent {
    object ShowTrashDay : Navigation()
    object ShowAddressInput : Navigation()
}

sealed class AddressInputScreenAction : BaseAction {
    data class UserSubmittedAddress(val address: String) : AddressInputScreenAction()
    data class UserInputAddressText(val text: String) : AddressInputScreenAction()
    object AddressValidatedSuccess : AddressInputScreenAction()
    object AddressValidatedError : AddressInputScreenAction()
}