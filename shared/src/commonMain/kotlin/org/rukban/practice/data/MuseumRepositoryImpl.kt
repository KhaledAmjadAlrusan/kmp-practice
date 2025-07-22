package org.rukban.practice.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import org.rukban.practice.domain.MuseumRepository

class MuseumRepositoryImpl(
    private val museumApi: MuseumApi,
    private val museumStorage: MuseumStorage,
): MuseumRepository {

    override suspend fun refresh() {
        museumStorage.saveObjects(museumApi.getData())
    }

    override suspend fun getMuseums(): List<MuseumObject> {
        if (museumStorage.getObjects().isEmpty()) {
            refresh()
        }
        return museumStorage.getObjects()
    }

    override suspend fun getMuseum(id: Int): MuseumObject? {
        return museumStorage.getObjectById(id)
    }
}