package com.llamalabb.phillygarbageday.domain.di

import com.llamalabb.phillygarbageday.data.local.dao.HeadlineDAO
import com.llamalabb.phillygarbageday.data.local.service.IAddressRealmService
import com.llamalabb.phillygarbageday.data.local.service.AddressRealmService
import com.llamalabb.phillygarbageday.data.remote.service.IPhillyApiService
import com.llamalabb.phillygarbageday.data.remote.service.PhillyApiService
import com.llamalabb.phillygarbageday.data.repository.IAddressRepository
import com.llamalabb.phillygarbageday.data.repository.AddressRepository
import com.llamalabb.phillygarbageday.platformModule
import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module


fun initKoin(
    enableNetworkLogs: Boolean = false,
    appDeclaration: KoinAppDeclaration = {}
) =
    startKoin {
        appDeclaration()
        modules(commonModule(enableNetworkLogs = enableNetworkLogs))
    }

// called by iOS etc
fun initKoin() = initKoin(enableNetworkLogs = true) {}

fun commonModule(enableNetworkLogs: Boolean) =
    getUseCaseModule() + getDateModule(enableNetworkLogs) + platformModule() + getHelperModule()

fun getHelperModule() = module { }

fun getDateModule(enableNetworkLogs: Boolean) = module {

    single<IAddressRepository> { AddressRepository(get(), get()) }

    single<IPhillyApiService> { PhillyApiService(get()) }

    single<IAddressRealmService> { AddressRealmService(get()) }

    single { Realm.open(RealmConfiguration.Builder(schema = setOf(HeadlineDAO::class)).build()) }

    single { createJson() }

    single { createHttpClient(get(), get(), enableNetworkLogs = enableNetworkLogs) }

}

fun getUseCaseModule() = module {
//    single {
//        AddToReadLaterUseCase(get())
//    }
//    single {
//        GetReadLaterUseCase(get())
//    }
//    single {
//        GetHeadlinesUseCase(get())
//    }
}


fun createHttpClient(
    httpClientEngine: HttpClientEngine,
    json: Json,
    enableNetworkLogs: Boolean
) =
    HttpClient(httpClientEngine) {

        install(ContentNegotiation) { json(json) }

        if (enableNetworkLogs) {
            install(Logging) {
                logger = Logger.SIMPLE
                level = LogLevel.ALL
            }
        }
    }

fun createJson() = Json { isLenient = true; ignoreUnknownKeys = true }

