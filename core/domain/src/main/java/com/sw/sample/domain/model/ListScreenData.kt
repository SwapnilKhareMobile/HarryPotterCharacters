package com.sw.sample.domain.model

data class ListScreenData(
    val id: String,
    val charName: String,
    val actorName: String,
    val species: String,
    val house: String,
    val dateOfBirth: String?,
    val image: String,
    val isAlive: Boolean,
)
