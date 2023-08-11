package com.thiagomonteiro.core.domain.model

data class Card(
    val id: String,
    val cardId: String,
    val name: String,
    val image: String?,
    val flavorDescription: String?,
    val shortDescription: String?,
    val cardSet: String,
    val type: String,
    val faction: String?,
    val rarity: String?,
    val attack: Int?,
    val cost: Int?,
    val health: Int?
)
