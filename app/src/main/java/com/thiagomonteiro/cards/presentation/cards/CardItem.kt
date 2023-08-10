package com.thiagomonteiro.cards.presentation.cards

import com.thiagomonteiro.cards.presentation.common.ListItem

data class CardItem(
    val id: Int,
    val name: String,
    override val key: Long = id.toLong()
) : ListItem
