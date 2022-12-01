package com.llamalabb.phillygarbageday.presentation.addressinput

import com.llamalabb.phillygarbageday.data.remote.dto.AddressDataDTO
import com.llamalabb.phillygarbageday.data.remote.dto.asDomainModel
import com.llamalabb.phillygarbageday.data.repository.IAddressRepository
import com.llamalabb.phillygarbageday.presentation.BaseAction
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.llamalabb.phillygarbageday.presentation.addressinput.AddressInputScreenAction.*
import kotlinx.coroutines.launch

class AddressInputViewModel(private val repo: IAddressRepository) : ViewModel() {

    private val _state = MutableStateFlow(AddressInputScreenState())
    val state = _state.asStateFlow()

    fun dispatch(action: BaseAction) {
        reduce(action, _state.value)
        launchSideEffects(action)
    }

    /** Reducers */
    //region Reducers

    private fun reduce(action: BaseAction, state: AddressInputScreenState) {
        _state.value = state.copy(
            isLoading = reduceIsLoading(action, state),
            addressInput = reduceAddressInput(action, state),
            trashPickUpDay = reduceTrashPickUpDay(action, state),
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
        is DataLoadError -> "Please enter a valid address"
        is DataLoadSuccess -> null
        else -> state.addressInputError
    }

    private fun reduceIsLoading(
        action: BaseAction,
        state: AddressInputScreenState
    ): Boolean = when (action) {
        is UserTappedLoadData -> true
        is DataLoadSuccess -> false
        is DataLoadError -> false
        else -> state.isLoading
    }

    private fun reduceTrashPickUpDay(
        action: BaseAction,
        state: AddressInputScreenState
    ): String = when (action) {
        is DataLoadSuccess -> action.dayOfPickUp
        else -> state.trashPickUpDay
    }

    //endregion

    /** Side Effects */
    //region Side Effects

    private fun launchSideEffects(action: BaseAction) {
        when (action) {
            is UserTappedLoadData -> loadAddressDataSideEffect(action.address)
            else -> Unit
        }
    }

    private fun loadAddressDataSideEffect(address: String) {
        viewModelScope.launch {
            val response = AddressDataDTO(repo.getAddressInfo(address).features)
            response.asDomainModel().garbageDay?.let { result ->
                dispatch(DataLoadSuccess(result.name))
            } ?: dispatch(DataLoadError)
        }
    }

    //endregion
}

sealed class AddressInputScreenAction : BaseAction {
    data class UserTappedLoadData(val address: String) : AddressInputScreenAction()
    data class UserInputAddressText(val text: String) : AddressInputScreenAction()
    data class DataLoadSuccess(val dayOfPickUp: String) : AddressInputScreenAction()
    object DataLoadError : AddressInputScreenAction()
}