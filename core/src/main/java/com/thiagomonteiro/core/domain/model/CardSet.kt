package com.thiagomonteiro.core.domain.model

data class CardSet(
    val basic: List<Card>,
    val classic: List<Card>,
    val naxxramas: List<Card>
)
