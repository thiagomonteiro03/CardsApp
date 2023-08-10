package com.thiagomonteiro.cards.presentation.cards

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.thiagomonteiro.cards.presentation.extensions.watchStatus
import com.thiagomonteiro.core.domain.model.CardSet
import com.thiagomonteiro.core.usecase.GetCardsUseCase
import com.thiagomonteiro.core.usecase.base.CoroutinesDispatchers
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CardsViewModel @Inject constructor(
    private val coroutinesDispatchers: CoroutinesDispatchers,
    private val getCardsUseCase: GetCardsUseCase,
) : ViewModel() {

    private val action = MutableLiveData<Action>()
    val state: LiveData<UiState> = action.switchMap {
        liveData(coroutinesDispatchers.main()) {
            when (it) {
                is Action.Load -> {
                    getCardsUseCase.invoke().watchStatus(
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
        action.value = Action.Load
    }

    sealed class UiState {
        object Loading : UiState()
        data class Success(val cardSet: CardSet) : UiState()
        class Error(val throwable: Throwable) : UiState()
        object Empty : UiState()
    }

    sealed class Action {
        object Load : Action()
    }
}