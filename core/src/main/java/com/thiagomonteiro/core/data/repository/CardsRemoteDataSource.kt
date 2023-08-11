package com.thiagomonteiro.core.data.repository

import com.thiagomonteiro.core.domain.model.Card
import com.thiagomonteiro.core.domain.model.Sets

interface CardsRemoteDataSource {

    suspend fun fetchAllCards(): Sets

    suspend fun fetchCardsBySet(cardSet: String): List<Card>

    suspend fun fetchCardById(cardId: String): Card

}