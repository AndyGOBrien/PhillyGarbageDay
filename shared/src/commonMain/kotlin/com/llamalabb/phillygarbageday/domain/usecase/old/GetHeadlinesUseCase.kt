package com.llamalabb.phillygarbageday.domain.usecase.old

//import com.llamalabb.phillygarbageday.data.remote.dto.asDomainModel
//import com.llamalabb.phillygarbageday.data.repository.IAddressRepository
//import kotlinx.coroutines.flow.flow
//
//class GetHeadlinesUseCase(
//    private val repository: IAddressRepository
//) {
//
//    operator fun invoke(page: Int, pageSize: Int = 20, country: String = "us") = flow {
//
//        val response =
//            repository.getAllHeadlines(page = page, pageSize = pageSize, country = country)
//                .asDomainModel()
//
//        emit(response)
//
//    }
//}