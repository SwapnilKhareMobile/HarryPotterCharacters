package com.sw.sample.domain

import com.sw.sample.data.repo.CharRepository
import com.sw.sample.domain.model.ListScreenData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetCharUseCase @Inject constructor(private val charRepository: CharRepository) : CharUseCase {

    override suspend fun invoke(): Flow<List<ListScreenData>?> {
        return charRepository.getChars().map { it ->
            it.body()?.map {
                ListScreenData(
                    id = it.id,
                    charName = it.name,
                    actorName = it.actor,
                    species = it.species,
                    house = it.house,
                    dateOfBirth = it.dateOfBirth,
                    image = it.image,
                    isAlive = it.alive
                )
            }
        }
    }
    override suspend fun getCharById(id: String): Flow<ListScreenData> = invoke().map { items ->
        items?.firstOrNull { model -> model.id == id }
            ?: throw NoSuchElementException("$id not found")
    }
}