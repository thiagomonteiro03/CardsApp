package com.thiagomonteiro.cards.framework.di

import com.thiagomonteiro.cards.framework.imageloader.GlideImageLoader
import com.thiagomonteiro.cards.framework.imageloader.ImageLoader
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
interface AppModule {

    @Binds
    fun bindImageLoader(imageLoader: GlideImageLoader): ImageLoader
}