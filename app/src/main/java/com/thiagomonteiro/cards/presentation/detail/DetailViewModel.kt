package com.thiagomonteiro.cards.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.thiagomonteiro.cards.presentation.extensions.watchStatus
import com.thiagomonteiro.core.domain.model.Card
import com.thiagomonteiro.core.usecase.GetCardByIdUseCase
import com.thiagomonteiro.core.usecase.base.CoroutinesDispatchers
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val coroutinesDispatchers: CoroutinesDispatchers,
    private val getCardByIdUseCase: GetCardByIdUseCase,
) : ViewModel() {

    private val action = MutableLiveData<Action>()
    val state: LiveData<DetailUiState> = action.switchMap {
        liveData(coroutinesDispatchers.main()) {
            when (it) {
                is Action.Load-> {
                    getCardByIdUseCase.invoke(
                        GetCardByIdUseCase.GetCardByIdParams(it.cardId)
                    ).watchStatus(
                        loading = {
                            emit(DetailUiState.Loading)
                        },
                        success = { data ->
                            // Sending only one cardSet because this feature is not yet implemented
                            emit(DetailUiState.Success(data))
                        },
                        error = {
                            emit(DetailUiState.Error(it))
                        }
                    )
                }
            }
        }
    }


    fun loadData(cardId: String) {
        action.value = Action.Load(cardId)
    }


    sealed class DetailUiState {
        object Loading : DetailUiState()
        data class Success(val card: Card) : DetailUiState()
        class Error(val throwable: Throwable) : DetailUiState()
    }

    sealed class Action {
        data class Load(val cardId: String) : Action()
    }

}