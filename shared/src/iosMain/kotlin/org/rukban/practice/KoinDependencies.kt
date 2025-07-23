package org.rukban.practice

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.rukban.practice.domain.MuseumRepository

class KoinDependencies : KoinComponent {
    val museumRepository: MuseumRepository by inject()
}