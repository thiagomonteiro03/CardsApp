package com.thiagomonteiro.core.data.repository

import com.thiagomonteiro.core.domain.model.Card
import com.thiagomonteiro.core.domain.model.CardSet

interface CardsRepository {

    suspend fun getAllCards(): CardSet

    suspend fun getCardsBySet(cardSet: String): List<Card>

}