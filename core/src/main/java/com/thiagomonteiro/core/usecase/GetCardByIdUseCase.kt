package com.thiagomonteiro.core.usecase

import com.thiagomonteiro.core.data.repository.CardsRepository
import com.thiagomonteiro.core.domain.model.Card
import com.thiagomonteiro.core.usecase.base.CoroutinesDispatchers
import com.thiagomonteiro.core.usecase.base.ResultStatus
import com.thiagomonteiro.core.usecase.base.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject


interface GetCardByIdUseCase {

    operator fun invoke(params: GetCardByIdParams): Flow<ResultStatus<Card>>

    data class GetCardByIdParams(val cardId: String)

}

class GetCardByIdUseCaseImpl @Inject constructor(
    private val repository: CardsRepository,
    private val dispatchers: CoroutinesDispatchers
) : UseCase<GetCardByIdUseCase.GetCardByIdParams, Card>(), GetCardByIdUseCase {

    override suspend fun doWork(
        params: GetCardByIdUseCase.GetCardByIdParams
    ): ResultStatus<Card> {
        return withContext(dispatchers.io()) {
            val card = repository.getCardById(params.cardId)

            ResultStatus.Success(card)
        }
    }
}