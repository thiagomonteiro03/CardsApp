package com.thiagomonteiro.core.usecase

import com.thiagomonteiro.core.data.repository.CardsRepository
import com.thiagomonteiro.core.domain.model.Card
import com.thiagomonteiro.core.usecase.base.CoroutinesDispatchers
import com.thiagomonteiro.core.usecase.base.ResultStatus
import com.thiagomonteiro.core.usecase.base.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject


interface GetCardsBySetUseCase {

    operator fun invoke(params: GetCardsBySetParams): Flow<ResultStatus<List<Card>>>

    data class GetCardsBySetParams(val cardSet: String)

}

class GetCardsBySetUseCaseImpl @Inject constructor(
    private val repository: CardsRepository,
    private val dispatchers: CoroutinesDispatchers
) : UseCase<GetCardsBySetUseCase.GetCardsBySetParams, List<Card>>(), GetCardsBySetUseCase {

    override suspend fun doWork(
        params: GetCardsBySetUseCase.GetCardsBySetParams
    ): ResultStatus<List<Card>> {
        return withContext(dispatchers.io()) {
            val cards = repository.getCardsBySet(params.cardSet)

            ResultStatus.Success(cards)
        }
    }
}