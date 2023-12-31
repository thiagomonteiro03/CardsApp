package com.thiagomonteiro.cards.framework.remote

import com.thiagomonteiro.cards.framework.network.CardsApi
import com.thiagomonteiro.cards.framework.network.response.toCardModel
import com.thiagomonteiro.cards.framework.network.response.toCardSetModel
import com.thiagomonteiro.core.data.repository.CardsRemoteDataSource
import com.thiagomonteiro.core.domain.model.Card
import com.thiagomonteiro.core.domain.model.Sets
import javax.inject.Inject

class RetrofitCardsDataSource @Inject constructor(
    private val cardsApi: CardsApi
) : CardsRemoteDataSource {

    override suspend fun fetchAllCards(): Sets {
        return cardsApi.getCards().toCardSetModel()
    }

    override suspend fun fetchCardsBySet(cardSet: String): List<Card> {
        return cardsApi.getCardsBySet(cardSet).map {
            it.toCardModel()
        }
    }

    override suspend fun fetchCardById(cardId: String): Card {
        return cardsApi.getCardById(cardId).first().toCardModel()
    }

}