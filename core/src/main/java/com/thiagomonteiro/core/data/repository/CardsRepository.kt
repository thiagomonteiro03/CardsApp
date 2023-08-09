package com.thiagomonteiro.core.data.repository

import com.thiagomonteiro.core.domain.model.CardSet

interface CardsRepository {

    suspend fun getCards(): CardSet

}