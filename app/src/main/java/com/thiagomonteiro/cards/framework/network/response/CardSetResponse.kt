package com.thiagomonteiro.cards.framework.network.response

import com.google.gson.annotations.SerializedName
import com.thiagomonteiro.core.domain.model.Sets

data class CardSetResponse(
    @SerializedName("Basic")
    val basic: List<CardResponse>,
    @SerializedName("Classic")
    val classic: List<CardResponse>,
    @SerializedName("Naxxramas")
    val naxxramas: List<CardResponse>,
)

fun CardSetResponse.toCardSetModel(): Sets {
    return Sets(
        basic = basic.map { it.toCardModel() },
        classic = classic.map { it.toCardModel() },
        naxxramas = naxxramas.map { it.toCardModel() }
    )
}
