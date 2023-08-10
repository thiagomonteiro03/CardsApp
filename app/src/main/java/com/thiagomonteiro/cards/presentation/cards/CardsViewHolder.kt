package com.thiagomonteiro.cards.presentation.cards

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.thiagomonteiro.cards.presentation.common.GenericViewHolder
import com.thiagomonteiro.cards.databinding.ItemCardBinding

class CardsViewHolder(
    itemBinding: ItemCardBinding,
//    private val imageLoader: ImageLoader
) : GenericViewHolder<CardItem>(itemBinding) {

    private val textName: TextView = itemBinding.textName
//    private val imageCharacter: ImageView = itemBinding.imageCharacter

    override fun bind(data: CardItem) {
        textName.text = data.name
//        imageLoader.load(imageCharacter, data.imageUrl)
    }

    companion object {
//        fun create(parent: ViewGroup, imageLoader: ImageLoader): FavoritesViewHolder {
    fun create(parent: ViewGroup): CardsViewHolder {
            val itemBinding = ItemCardBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)

//            return CardsViewHolder(itemBinding, imageLoader)
            return CardsViewHolder(itemBinding)
        }
    }
}