package org.rukban.practice.domain

import org.rukban.practice.data.MuseumObject

interface MuseumRepository {
    suspend fun refresh()
    suspend fun getMuseums(): List<MuseumObject>
    suspend fun getMuseum(id: Int): MuseumObject?
}