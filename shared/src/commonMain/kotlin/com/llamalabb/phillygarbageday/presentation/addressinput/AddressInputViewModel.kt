package com.llamalabb.phillygarbageday.presentation.addressinput

import com.llamalabb.phillygarbageday.data.remote.dto.AddressDataDTO
import com.llamalabb.phillygarbageday.data.remote.dto.asDomainModel
import com.llamalabb.phillygarbageday.data.repository.AbstractRepository
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.llamalabb.phillygarbageday.presentation.addressinput.AddressInputScreenAction.*
import kotlinx.coroutines.launch

class AddressInputViewModel(private val repo: AbstractRepository) : ViewModel() {

    private val _state = MutableStateFlow(AddressInputScreenState())
    val state = _state.asStateFlow()

    private fun reduce(action: BaseAction, state: AddressInputScreenState) {
        _state.value = state.copy(
            isLoading = reduceIsLoading(action, state),
            addresses = reduceAddresses(action, state),
            addressInput = reduceAddressInput(action, state),
            trashPickUpDay = reduceTrashPickUpDay(action, state)
        )
    }

    private fun reduceAddressInput(
        action: BaseAction,
        state: AddressInputScreenState
    ): String = when (action) {
        is UserInputAddressText -> action.text
        is UserTappedAddressItem -> "${action.addressItem.streetAddress}, ${action.addressItem.zipCode}"
        is UserTappedClearContent -> ""
        else -> state.addressInput
    }

    private fun reduceIsLoading(
        action: BaseAction,
        state: AddressInputScreenState
    ): Boolean = when (action) {
        is UserTappedLoadData -> true
        is DataLoadSuccess -> false
        else -> state.isLoading
    }

    private fun reduceAddresses(
        action: BaseAction,
        state: AddressInputScreenState
    ): List<AddressItem> = when (action) {
        is DataLoadSuccess -> action.addressList
        is UserTappedClearContent -> emptyList()
        else -> state.addresses
    }

    private fun reduceTrashPickUpDay(
        action: BaseAction,
        state: AddressInputScreenState
    ): String = when (action) {
        is DataLoadSuccess -> action.dayOfPickUp
        else -> state.trashPickUpDay
    }

    private fun loadDataSideEffect(action: UserTappedLoadData) {
        viewModelScope.launch {
            val result = AddressDataDTO(repo.getAddressInfo(action.address).features)
            dispatch(
                DataLoadSuccess(
                    result.asDomainModel().garbageDay.toString(),
                    createMockAddressList()
                )
            )
        }
    }

    fun dispatch(action: BaseAction) {
        reduce(action, _state.value)
        when (action) {
            is UserTappedLoadData -> loadDataSideEffect(action)
            else -> Unit
        }
    }

    private fun createMockAddressList() = (1..10).map {
        AddressItem("$it Random St", "$it$it$it$it$it", "$it km")
    }
}

sealed class AddressInputScreenAction : BaseAction {
    object UserTappedClearContent : AddressInputScreenAction()
    data class UserTappedLoadData(val address: String) : AddressInputScreenAction()
    data class UserTappedAddressItem(val addressItem: AddressItem) : AddressInputScreenAction()
    data class UserInputAddressText(val text: String) : AddressInputScreenAction()
    data class DataLoadSuccess(
        val dayOfPickUp: String,
        val addressList: List<AddressItem>
    ) : AddressInputScreenAction()
}

interface BaseAction