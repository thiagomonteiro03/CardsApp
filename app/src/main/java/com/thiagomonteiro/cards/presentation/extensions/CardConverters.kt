package com.thiagomonteiro.cards.presentation.extensions

import com.thiagomonteiro.cards.presentation.cards.CardItem
import com.thiagomonteiro.core.domain.model.Card

fun Card.toCardItem(): CardItem = CardItem(id.toLong(), cardId, name, cardSet, image)

fun List<Card>.toCardItemList(): List<CardItem> = map {
    it.toCardItem()
}
