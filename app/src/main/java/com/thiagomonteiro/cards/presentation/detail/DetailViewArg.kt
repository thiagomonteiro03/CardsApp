package com.thiagomonteiro.cards.presentation.detail

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class DetailViewArg(
    val cardId: String,
    val name: String,
    val cardSet: String,
    val imageUrl: String
): Parcelable
