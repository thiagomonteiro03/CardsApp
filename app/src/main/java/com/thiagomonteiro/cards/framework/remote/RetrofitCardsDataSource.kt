package com.thiagomonteiro.cards.framework.remote

import com.thiagomonteiro.cards.framework.network.CardsApi
import com.thiagomonteiro.cards.framework.network.response.toCardSetModel
import com.thiagomonteiro.core.data.repository.CardsRemoteDataSource
import com.thiagomonteiro.core.domain.model.CardSet
import javax.inject.Inject

class RetrofitCardsDataSource @Inject constructor(
    private val cardsApi: CardsApi
) : CardsRemoteDataSource {

    override suspend fun fetchCards(): CardSet {
        return cardsApi.getCards().toCardSetModel()
    }

}