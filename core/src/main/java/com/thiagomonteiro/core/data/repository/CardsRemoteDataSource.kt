package com.thiagomonteiro.core.data.repository

import com.thiagomonteiro.core.domain.model.Card
import com.thiagomonteiro.core.domain.model.CardSet

interface CardsRemoteDataSource {

    suspend fun fetchAllCards(): CardSet

    suspend fun fetchCardsBySet(cardSet: String): List<Card>

}