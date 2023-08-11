package com.thiagomonteiro.testing.model

import com.thiagomonteiro.core.domain.model.Card

class CardFactory {

    fun createBasic(card: Basic) = when (card) {
        Basic.Demonfire -> Card(
            id = "26",
            cardId = "EX1_596e",
            name = "Demonfire",
            image = null,
            flavorDescription = null,
            shortDescription = null,
            cardSet = "Classic",
            type = "Enchantment",
            faction = "Neutral",
            rarity = "Common",
            attack = null,
            cost = 0,
            health = null
        )

        Basic.InnerRage -> Card(
            id = "22",
            cardId = "EX1_607",
            name = "Inner Rage",
            image = "https://d15f34w2p8l1cc.cloudfront.net/hearthstone/bc286530a2962c07e7784d4f034ef3375fce67f779ef68c98ce7551753b20ab6.png",
            flavorDescription = "They're only smiling on the outside.",
            shortDescription = null,
            cardSet = "Classic",
            type = "Spell",
            faction = "Neutral",
            rarity = "Common",
            attack = null,
            cost = 0,
            health = null
        )
    }

    sealed class Basic {
        object Demonfire : Basic()
        object InnerRage : Basic()
    }
}