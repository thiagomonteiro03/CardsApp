package com.thiagomonteiro.cards.framework

import com.thiagomonteiro.core.data.repository.CardsRemoteDataSource
import com.thiagomonteiro.core.data.repository.CardsRepository
import com.thiagomonteiro.core.domain.model.Card
import com.thiagomonteiro.core.domain.model.CardSet
import javax.inject.Inject

class CardsRepositoryImpl @Inject constructor(
    private val remoteDataSource: CardsRemoteDataSource
): CardsRepository {

    override suspend fun getAllCards(): CardSet {
        return remoteDataSource.fetchAllCards()
    }

    override suspend fun getCardsBySet(cardSet: String): List<Card> {
        return remoteDataSource.fetchCardsBySet(cardSet)
    }


}