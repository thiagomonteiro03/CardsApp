package com.thiagomonteiro.cards.framework.di

import com.thiagomonteiro.cards.framework.CardsRepositoryImpl
import com.thiagomonteiro.cards.framework.remote.RetrofitCardsDataSource
import com.thiagomonteiro.core.data.repository.CardsRemoteDataSource
import com.thiagomonteiro.core.data.repository.CardsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface CardsRepositoryModule {

    @Binds
    fun bindCardsRepository(repository: CardsRepositoryImpl): CardsRepository

    @Binds
    fun bindRemoteDataSource(dataSource: RetrofitCardsDataSource): CardsRemoteDataSource

}