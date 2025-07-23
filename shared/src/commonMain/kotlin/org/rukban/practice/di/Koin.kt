package org.rukban.practice.di

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module
import org.rukban.practice.data.InMemoryMuseumStorage
import org.rukban.practice.data.KtorMuseumApi
import org.rukban.practice.data.MuseumApi
import org.rukban.practice.data.MuseumRepositoryImpl
import org.rukban.practice.data.MuseumStorage
import org.rukban.practice.domain.MuseumRepository
import org.rukban.practice.screens.DetailViewModel
import org.rukban.practice.screens.ListViewModel

val dataModule = module {
    single {
        val json = Json { ignoreUnknownKeys = true }
        HttpClient {
            install(ContentNegotiation) {
                // TODO Fix API so it serves application/json
                json(json, contentType = ContentType.Any)
            }
        }
    }

    single<MuseumApi> { KtorMuseumApi(get()) }
    single<MuseumStorage> { InMemoryMuseumStorage() }
    single<MuseumRepository> {
        MuseumRepositoryImpl(get(), get())
    }
}


fun initKoin(extraModules: List<Module> = emptyList()) {
    startKoin {
        modules(
            dataModule,
            *extraModules.toTypedArray(),
        )
    }
}

fun initKoinIOS() {
    initKoin(
        listOf(
//            module {
//                factory { ListViewModel(get()) }
//                factory { DetailViewModel(get()) }
//            }
        )
    )
}
