package com.thiagomonteiro.cards.framework.network

import com.thiagomonteiro.cards.framework.network.response.CardResponse
import com.thiagomonteiro.cards.framework.network.response.CardSetResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface CardsApi {

    @GET("cards")
    suspend fun getCards(): CardSetResponse

    @GET("cards/sets/{cardSet}")
    suspend fun getCardsBySet(
        @Path("cardSet") cardSet: String
    ): List<CardResponse>

    @GET("cards/{cardId}")
    suspend fun getCardById(
        @Path("cardId") cardId: String
    ): List<CardResponse>

}