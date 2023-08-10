package com.thiagomonteiro.core.usecase

import com.thiagomonteiro.core.data.repository.CardsRepository
import com.thiagomonteiro.core.domain.model.CardSet
import com.thiagomonteiro.core.usecase.base.CoroutinesDispatchers
import com.thiagomonteiro.core.usecase.base.ResultStatus
import com.thiagomonteiro.core.usecase.base.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject


interface GetCardsUseCase {

    operator fun invoke(params: Unit = Unit): Flow<ResultStatus<CardSet>>

}

class GetCardsUseCaseImpl @Inject constructor(
    private val repository: CardsRepository,
    private val dispatchers: CoroutinesDispatchers
) : UseCase<Unit, CardSet>(), GetCardsUseCase {

    override suspend fun doWork(params: Unit): ResultStatus<CardSet> {
        return withContext(dispatchers.io()) {
            val cards = repository.getCards()

            ResultStatus.Success(cards)
        }
    }
}