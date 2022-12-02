package com.llamalabb.phillygarbageday.presentation.trashday

import com.llamalabb.phillygarbageday.data.repository.ITrashDayRepository
import com.llamalabb.phillygarbageday.domain.domain_model.AddressInfo
import com.llamalabb.phillygarbageday.domain.domain_model.Holiday
import com.llamalabb.phillygarbageday.presentation.BaseAction
import com.llamalabb.phillygarbageday.presentation.trashday.TrashDayAction.*
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TrashDayViewModel(private val repo: ITrashDayRepository) : ViewModel() {

    private val _state = MutableStateFlow(TrashDayState())
    val state = _state.asStateFlow()

    init { dispatch(LoadData) }

    fun dispatch(action: BaseAction) {
        reduce(action, _state.value)
        launchSideEffects(action)
    }

    /** Reducers */
    //region Reducers

    private fun reduce(action: BaseAction, state: TrashDayState) {
        _state.value = state.copy(
            isLoading = reduceIsLoading(action, state),
            trashPickupDay = reduceTrashPickupDay(action, state),
            holidays = reduceHolidays(action, state),
            streetAddress = reduceStreetAddress(action, state),
        )
    }

    private fun reduceIsLoading(
        action: BaseAction,
        state: TrashDayState
    ): Boolean = when (action) {
        is LoadData -> true
        is LoadDataSuccess -> false
        is LoadDataFailure -> false
        else -> state.isLoading
    }

    private fun reduceTrashPickupDay(
        action: BaseAction,
        state: TrashDayState
    ): String = when (action) {
        is LoadDataSuccess -> action.addressInfo.garbageDay.name
        is LoadDataFailure -> ""
        else -> state.trashPickupDay
    }

    private fun reduceHolidays(
        action: BaseAction,
        state: TrashDayState,
    ): List<Holiday> = when (action) {
        is LoadDataSuccess -> action.holidays
        is LoadDataFailure -> emptyList()
        else -> state.holidays
    }

    private fun reduceStreetAddress(
        action: BaseAction,
        state: TrashDayState
    ): String = when (action) {
        is LoadDataSuccess -> action.addressInfo.streetAddress
        is LoadDataFailure -> ""
        else -> state.streetAddress
    }

    //endregion

    /** Side Effects */
    //region Side Effects

    private fun launchSideEffects(action: BaseAction) {
        when (action) {
            is LoadData -> loadDataSideEffect()
            else -> Unit
        }
    }

    private fun loadDataSideEffect() {
        viewModelScope.launch {
            try {
                dispatch(LoadDataSuccess(repo.fetchAddressInfo(), repo.fetchHolidays()))
            } catch (e: Exception) {
                dispatch(LoadDataFailure(e))
            }
        }
    }

    //endregion
}

sealed class TrashDayAction : BaseAction {
    object LoadData : TrashDayAction()
    data class LoadDataSuccess(
        val addressInfo: AddressInfo,
        val holidays: List<Holiday>
    ) : TrashDayAction()
    data class LoadDataFailure(val error: Exception) : TrashDayAction()
}