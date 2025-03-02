package com.sw.sample.potterchar.ui.list.model

data class ListScreenData(
    val charName: String,
    private val actorName: String,
    private val species: String,
    private val house: String,
    private val dateOfBirth: String?,
    private val image: String,
    private val isAlive: Boolean,
)
