package com.thiagomonteiro.cards.presentation.cards

import com.thiagomonteiro.cards.presentation.common.ListItem

data class CardItem(
    val id: Long,
    val cardId: String,
    val name: String,
    val cardSet: String,
    val image: String?,
    override val key: Long = id
) : ListItem
