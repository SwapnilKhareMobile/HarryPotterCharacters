package com.sw.sample.domain

import com.sw.sample.data.repo.CharRepository
import com.sw.sample.potterchar.ui.list.model.ListScreenData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetCharUseCase @Inject constructor(private val charRepository: CharRepository) : CharUseCase {
    override suspend fun invoke(): Flow<List<ListScreenData>?> {
        return charRepository.getChars().map { it ->
            it.body()?.map {
                ListScreenData(
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
}