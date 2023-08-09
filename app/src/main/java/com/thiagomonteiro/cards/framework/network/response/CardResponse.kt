package com.thiagomonteiro.cards.framework.network.response

import com.google.gson.annotations.SerializedName
import com.thiagomonteiro.core.domain.model.Card

data class CardResponse(
    @SerializedName("cardId")
    val cardId: String,
    @SerializedName("name")
    val name: String
)

fun CardResponse.toCardModel(): Card {
    return Card(
        id = cardId,
        name = name
    )
}