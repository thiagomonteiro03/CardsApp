package com.thiagomonteiro.cards.framework.network.response

import com.google.gson.annotations.SerializedName
import com.thiagomonteiro.core.domain.model.Card

data class CardResponse(
    @SerializedName("dbfId")
    val id: String,
    @SerializedName("cardId")
    val cardId: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("img")
    val image: String?,
    @SerializedName("flavor")
    val flavorDescription: String?,
    @SerializedName("text")
    val shortDescription: String?,
    @SerializedName("cardSet")
    val cardSet: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("faction")
    val faction: String?,
    @SerializedName("rarity")
    val rarity: String?,
    @SerializedName("attack")
    val attack: Int?,
    @SerializedName("cost")
    val cost: Int?,
    @SerializedName("health")
    val health: Int?,
)

fun CardResponse.toCardModel(): Card {
    return Card(
        id = id,
        cardId = cardId,
        name = name,
        image = image,
        flavorDescription = flavorDescription,
        shortDescription = shortDescription,
        cardSet = cardSet,
        type = type,
        faction = faction,
        rarity = rarity,
        attack = attack,
        cost = cost,
        health = health
    )
}