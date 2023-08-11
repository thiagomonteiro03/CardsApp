package com.thiagomonteiro.cards.presentation.cards

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.thiagomonteiro.cards.presentation.common.GenericViewHolder
import com.thiagomonteiro.cards.databinding.ItemCardBinding
import com.thiagomonteiro.cards.framework.imageloader.ImageLoader

class CardsViewHolder(
    itemBinding: ItemCardBinding,
    private val imageLoader: ImageLoader
) : GenericViewHolder<CardItem>(itemBinding) {

    private val textName: TextView = itemBinding.textName
    private val image: ImageView = itemBinding.imageCard

    override fun bind(data: CardItem) {
        textName.text = data.name
        imageLoader.load(image, data.image?: "")
    }

    companion object {
        fun create(parent: ViewGroup, imageLoader: ImageLoader): CardsViewHolder {
            val itemBinding = ItemCardBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)

            return CardsViewHolder(itemBinding, imageLoader)
        }
    }
}