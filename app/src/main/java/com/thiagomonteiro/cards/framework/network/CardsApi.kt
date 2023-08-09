package com.thiagomonteiro.cards.framework.network

import com.thiagomonteiro.cards.framework.network.response.CardSetResponse
import retrofit2.http.GET

interface CardsApi {

//    @GET("cards")
//    suspend fun getCards(
//        @Path("characterId")
//        characterId: Int
//    ): Response<CardSetResponse>

    @GET("cards")
    suspend fun getCards(): CardSetResponse

}