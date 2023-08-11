package com.thiagomonteiro.cards.presentation.enums

import androidx.annotation.StringRes
import com.thiagomonteiro.cards.R
import com.thiagomonteiro.cards.framework.network.NetworkConstants

enum class CardSetType(@StringRes val label: Int, val queryParameter: String) {

    BASIC_CARD_SET( R.string.card_set_basic, NetworkConstants.QUERY_PARAMETER_BASIC),
    CLASSIC_CARD_SET( R.string.card_set_classic, NetworkConstants.QUERY_PARAMETER_CLASSIC),
    NAXXRAMAS_CARD_SET( R.string.card_set_naxxramas, NetworkConstants.QUERY_PARAMETER_NAXXRAMAS)

}