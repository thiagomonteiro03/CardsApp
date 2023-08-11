package com.thiagomonteiro.cards.presentation.cards

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import com.thiagomonteiro.core.usecase.GetCardsBySetUseCase
import com.thiagomonteiro.core.usecase.GetCardsUseCase
import com.thiagomonteiro.core.usecase.base.ResultStatus
import com.thiagomonteiro.testing.MainCoroutineRule
import com.thiagomonteiro.testing.model.CardFactory
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class CardsViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutinesRule = MainCoroutineRule()

    private lateinit var viewModel: CardsViewModel

    @Mock
    private lateinit var getCardsUseCase: GetCardsUseCase

    @Mock
    private lateinit var getCardsBySetUseCase: GetCardsBySetUseCase

    @Mock
    private lateinit var uiStateObserver: Observer<CardsViewModel.UiState>

    private val card = CardFactory().createBasic(CardFactory.Basic.InnerRage)
    private val cardItems = listOf(
        CardItem(card.id.toLong(), card.cardId,card.name, card.cardSet, card.image)
    )

    @Before
    fun setup(){
        viewModel  = CardsViewModel(
            mainCoroutinesRule.testDispatcherProvider,
            getCardsUseCase,
            getCardsBySetUseCase
        ).apply {
            state.observeForever(uiStateObserver)
        }
    }

    @Test
    fun `should notify uiState with Success when getCardsBySet is Success and have items`() = runTest {
        //Arrange
        whenever(getCardsBySetUseCase.invoke(any()))
            .thenReturn(
                flowOf(
                    ResultStatus.Success(
                        listOf(card)
                    )
                )
            )
        //Act
        viewModel.getCardsBySet(card.cardSet)
        val uiState = viewModel.state.value
        //Assert
        assertEquals(uiState, CardsViewModel.UiState.Success(listOf(card)))
    }

    @Test
    fun `should notify uiState with Empty when getCardsBySet is Success and is empty`() = runTest {
        //Arrange
        whenever(getCardsBySetUseCase.invoke(any()))
            .thenReturn(
                flowOf(
                    ResultStatus.Success(
                        listOf()
                    )
                )
            )
        //Act
        viewModel.getCardsBySet(card.cardSet)
        val uiState = viewModel.state.value
        //Assert
        assertEquals(uiState, CardsViewModel.UiState.Empty)
    }

    @Test
    fun `should notify uiState with Error when getCardsBySet throw a Error`() = runTest {
        //Arrange
        whenever(getCardsBySetUseCase.invoke(any()))
            .thenReturn(
                flowOf(
                    ResultStatus.Error(Throwable())
                )
            )
        //Act
        viewModel.getCardsBySet(card.cardSet)
        val uiState = viewModel.state.value
        //Assert
        assertEquals(uiState, CardsViewModel.UiState.Error)
    }

}