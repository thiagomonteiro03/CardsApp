package com.thiagomonteiro.core.data.repository

import com.thiagomonteiro.core.domain.model.Card
import com.thiagomonteiro.core.domain.model.Sets

interface CardsRepository {

    suspend fun getAllCards(): Sets

    suspend fun getCardsBySet(cardSet: String): List<Card>

    suspend fun getCardById(cardId: String): Card

}