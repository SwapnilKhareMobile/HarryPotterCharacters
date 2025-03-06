package com.sw.sample.domain

import com.sw.sample.data.repo.CharRepository
import com.sw.sample.db.model.DBDataResult
import com.sw.sample.domain.model.ListScreenData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
class GetCharUseCase @Inject constructor(private val charRepository: CharRepository) : CharUseCase {

    override suspend fun invoke(): Flow<List<ListScreenData>?> {
        return charRepository.getLocalCharList().flatMapLatest { localList ->
            if (localList.isNotEmpty()) {
                flowOf(localList.map { dbDataResult ->
                    ListScreenData(
                        id = dbDataResult.id,
                        charName = dbDataResult.charName,
                        actorName = dbDataResult.actorName,
                        species = dbDataResult.species,
                        house = dbDataResult.house,
                        dateOfBirth = dbDataResult.dateOfBirth,
                        image = dbDataResult.image,
                        isAlive = dbDataResult.isAlive
                    )
                })
            } else {
                // Fetch from remote if local list is empty
                charRepository.getChars().map { response ->
                    response.body()?.map { apiData ->
                        ListScreenData(
                            id = apiData.id,
                            charName = apiData.name,
                            actorName = apiData.actor,
                            species = apiData.species,
                            house = apiData.house,
                            dateOfBirth = apiData.dateOfBirth,
                            image = apiData.image,
                            isAlive = apiData.alive
                        )
                    }?.also { listScreenData ->
                        val dbList = listScreenData.map {
                            DBDataResult(
                                id = it.id,
                                charName = it.charName,
                                actorName = it.actorName,
                                species = it.species,
                                house = it.house,
                                dateOfBirth = it.dateOfBirth,
                                image = it.image,
                                isAlive = it.isAlive
                            )
                        }
                        charRepository.addIntoLocalCharList(dbList)
                    }
                }
            }
        }
    }
    override suspend fun getCharById(id: String): Flow<ListScreenData> {
        return charRepository.getLocalCharList().map { localList ->
            localList.firstOrNull { model -> model.id == id }
                ?.let { dbDataResult ->
                    ListScreenData(
                        id = dbDataResult.id,
                        charName = dbDataResult.charName,
                        actorName = dbDataResult.actorName,
                        species = dbDataResult.species,
                        house = dbDataResult.house,
                        dateOfBirth = dbDataResult.dateOfBirth,
                        image = dbDataResult.image,
                        isAlive = dbDataResult.isAlive
                    )
                } ?: throw NoSuchElementException("$id not found")
        }
    }
}