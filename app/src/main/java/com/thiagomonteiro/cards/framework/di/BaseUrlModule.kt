package com.thiagomonteiro.cards.framework.di

import com.thiagomonteiro.cards.BuildConfig
import com.thiagomonteiro.cards.framework.di.qualifier.BaseUrl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object BaseUrlModule {

    @BaseUrl
    @Provides
    fun provideBaseUrl(): String = BuildConfig.BASE_URL

}