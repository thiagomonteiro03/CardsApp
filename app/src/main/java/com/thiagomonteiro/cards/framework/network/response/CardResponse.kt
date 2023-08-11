package com.thiagomonteiro.cards.framework.network.response

import com.google.gson.annotations.SerializedName
import com.thiagomonteiro.core.domain.model.Card

data class CardResponse(
    @SerializedName("dbfId")
    val cardId: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("img")
    val image: String?
)

fun CardResponse.toCardModel(): Card {
    return Card(
        id = cardId,
        name = name,
        image = image
    )
}