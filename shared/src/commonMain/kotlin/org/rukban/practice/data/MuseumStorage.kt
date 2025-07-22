package org.rukban.practice.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

interface MuseumStorage {
    suspend fun saveObjects(newObjects: List<MuseumObject>)

    fun getObjectById(objectId: Int): MuseumObject?

    fun getObjects(): List<MuseumObject>
}

class InMemoryMuseumStorage : MuseumStorage {

    private val storedObjects = mutableMapOf<StoredType<*>, Any>()

    private fun <T> save(type: StoredType<T>, data: T) {
        storedObjects[type] = data as Any
    }

    private fun <T> get(type: StoredType<T>): T? {
        return storedObjects[type] as? T
    }

    override suspend fun saveObjects(newObjects: List<MuseumObject>) {
        save(StoredType.Museums, newObjects)
    }

    override fun getObjectById(objectId: Int): MuseumObject? {
        val objects = get(StoredType.Museums) ?: return null
        return objects.firstOrNull { it.objectID == objectId }
    }

    override fun getObjects(): List<MuseumObject> {
        return get(StoredType.Museums) ?: emptyList()
    }
}

sealed class StoredType<T> {
    data object Museums : StoredType<List<MuseumObject>>()
}

