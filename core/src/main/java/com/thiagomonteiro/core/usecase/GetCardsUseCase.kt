package com.thiagomonteiro.core.usecase

import com.thiagomonteiro.core.data.repository.CardsRepository
import com.thiagomonteiro.core.domain.model.Sets
import com.thiagomonteiro.core.usecase.base.CoroutinesDispatchers
import com.thiagomonteiro.core.usecase.base.ResultStatus
import com.thiagomonteiro.core.usecase.base.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject


interface GetCardsUseCase {

    operator fun invoke(params: Unit = Unit): Flow<ResultStatus<Sets>>

}

class GetCardsUseCaseImpl @Inject constructor(
    private val repository: CardsRepository,
    private val dispatchers: CoroutinesDispatchers
) : UseCase<Unit, Sets>(), GetCardsUseCase {

    override suspend fun doWork(params: Unit): ResultStatus<Sets> {
        return withContext(dispatchers.io()) {
            val cards = repository.getAllCards()

            ResultStatus.Success(cards)
        }
    }
}