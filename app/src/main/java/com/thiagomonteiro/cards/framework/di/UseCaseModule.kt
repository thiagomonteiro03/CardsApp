package com.thiagomonteiro.cards.framework.di

import com.thiagomonteiro.core.usecase.GetCardsBySetUseCase
import com.thiagomonteiro.core.usecase.GetCardsBySetUseCaseImpl
import com.thiagomonteiro.core.usecase.GetCardsUseCase
import com.thiagomonteiro.core.usecase.GetCardsUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface UseCaseModule {

    @Binds
    fun bindGetCardsUseCase(useCase: GetCardsUseCaseImpl): GetCardsUseCase

    @Binds
    fun bindGetCardsBySetUseCase(useCase: GetCardsBySetUseCaseImpl): GetCardsBySetUseCase
}