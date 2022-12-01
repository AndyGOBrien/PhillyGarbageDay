package com.llamalabb.phillygarbageday.presentation.old

//import com.llamalabb.phillygarbageday.domain.domain_model.HeadlineDomainModel
//import com.llamalabb.phillygarbageday.domain.usecase.newsdetails.AddToReadLaterUseCase
//import dev.icerock.moko.mvvm.viewmodel.ViewModel
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.asStateFlow
//import kotlinx.coroutines.flow.update
//import kotlinx.coroutines.launch
//
//class NewsDetailsViewModel(private val readLaterUseCase: AddToReadLaterUseCase) : ViewModel() {
//
//
//    private val _state = MutableStateFlow<NewsDetailsScreenState>(NewsDetailsScreenState.Idle)
//    val state = _state.asStateFlow()
//    fun onIntent(intent: NewsDetailsScreenEvent) {
//        when (intent) {
//            is NewsDetailsScreenEvent.SaveForLater -> {
//                addToReadLater(intent.headlineDomainModel)
//            }
//        }
//    }
//
//    private fun addToReadLater(headlineDomainModel: HeadlineDomainModel) {
//        _state.update {
//            NewsDetailsScreenState.SavingForLater
//        }
//        viewModelScope.launch(Dispatchers.Default) {
//            readLaterUseCase(headlineDomainModel)
//
//        }.invokeOnCompletion {
//            _state.update {
//                NewsDetailsScreenState.Success
//            }
//        }
//    }
//}
//
