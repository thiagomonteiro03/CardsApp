package com.thiagomonteiro.core.data.repository

import com.thiagomonteiro.core.domain.model.CardSet

interface CardsRemoteDataSource {

    suspend fun fetchCards(): CardSet

}