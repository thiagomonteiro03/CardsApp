package com.thiagomonteiro.cards.presentation.cards

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.thiagomonteiro.cards.presentation.extensions.watchStatus
import com.thiagomonteiro.core.domain.model.Card
import com.thiagomonteiro.core.usecase.GetCardsBySetUseCase
import com.thiagomonteiro.core.usecase.GetCardsUseCase
import com.thiagomonteiro.core.usecase.base.CoroutinesDispatchers
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CardsViewModel @Inject constructor(
    private val coroutinesDispatchers: CoroutinesDispatchers,
    private val getCardsUseCase: GetCardsUseCase,
    private val getCardsBySetUseCase: GetCardsBySetUseCase,
) : ViewModel() {

    private val action = MutableLiveData<Action>()
    val state: LiveData<UiState> = action.switchMap {
        liveData(coroutinesDispatchers.main()) {
            when (it) {
                is Action.GetAllCards -> {
                    getCardsUseCase.invoke().watchStatus(
                        loading = {
                            emit(UiState.Loading)
                        },
                        success = { data ->
                            // Sending only one cardSet because this feature is not yet implemented
                                emit(UiState.Success(data.basic))
                        },
                        error = {
                            emit(UiState.Error(it))
                        }
                    )
                }

                is Action.GetCardsBySet -> {
                    getCardsBySetUseCase.invoke(
                        GetCardsBySetUseCase.GetCardsBySetParams(it.cardSet)
                    ).watchStatus(
                        loading = {
                            emit(UiState.Loading)
                        },
                        success = { data ->
//                            if (data.basic.isEmpty())
//                                emit(UiState.Empty)
//                            else
                            emit(UiState.Success(data))
                        },
                        error = {
                            emit(UiState.Error(it))
                        }
                    )
                }
            }
        }
    }

    fun getAllCards() {
        action.value = Action.GetAllCards
    }

    fun getCardsBySet(cardSet: String) {
        action.value = Action.GetCardsBySet(cardSet)
    }

    sealed class UiState {
        object Loading : UiState()
        data class Success(val cards: List<Card>) : UiState()
        class Error(val throwable: Throwable) : UiState()
        object Empty : UiState()
    }

    sealed class Action {
        object GetAllCards : Action()
        data class GetCardsBySet(val cardSet: String) : Action()
    }
}